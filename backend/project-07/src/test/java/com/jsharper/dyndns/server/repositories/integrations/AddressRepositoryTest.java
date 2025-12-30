package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.Address;
import com.jsharper.dyndns.server.repositories.AddressRepository;
import com.jsharper.dyndns.server.repositories.integrations.iterables.AddressIterable;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository ar;

    private List<Address> inputAddresses;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeAll
    public void init() throws IOException {

        inputAddresses = this.getResource();

        Assertions.assertNotNull(inputAddresses);

        Assertions.assertFalse(inputAddresses.isEmpty());

        Supplier<Stream<Address>> products = () -> StreamSupport.stream(ar.saveAll(inputAddresses).spliterator(), false);

        Assertions.assertTrue(products.get().findAny().isPresent());
    }

    @AfterAll
    public void cleanUp() {
        ar.deleteAll();

        Assertions.assertEquals(0, ar.count());
    }


    @Test
    @Order(1)
    @DisplayName("create a simple Address Entity")
    void createSimpleAddressEntity_whenProvidedAddressInstance_returnAddressEntity() {

        var address = new Address("Test street", "Test City", "Test State", 23334);

        var storedAddress = ar.save(address);

        Assertions.assertEquals(storedAddress.getId(), address.getId());
        Assertions.assertEquals(storedAddress.getCity(), address.getCity());
        Assertions.assertEquals(storedAddress.getStreet(), address.getStreet());
        Assertions.assertEquals(storedAddress.getState(), address.getState());
        Assertions.assertEquals(storedAddress.getZipCode(), address.getZipCode());
        Assertions.assertEquals(storedAddress, address);
    }


    @Test
    @Order(2)
    @DisplayName("Find all Addresses with query and pageable")
    void findAllAddressesWithQueryAndPageable_whenProvidedListOfAddresses_returnListPageableListOfAddresses() {
        var initialPageNumber = 0;

        var initialPageSize = 4;

        Pageable pageable = PageRequest.of(initialPageNumber, initialPageSize);

        var result = ar.findAllAddresses(pageable);

        var sortedMapList = getSortedMapList(initialPageNumber, initialPageSize);

        int index = 0;

        while (result.hasNext()) {
            System.out.println(result.getTotalPages() + "\n " + result.getSize() + "\n " + result.getNumber());

            Page<Address> finalResult = result;

            Supplier<Stream<Address>> actualProductEntity = finalResult;
            var pairs = StreamUtils.zip(sortedMapList.get(index).stream(), actualProductEntity.get(), Pair::of);

            pairs.forEach(pair -> {
                var first = pair.getFirst();
                var second = pair.getSecond();
                assertEquals(first.getId(), second.getId());
                assertEquals(first.getStreet(), second.getStreet());
                assertEquals(first.getCity(), second.getCity());
                assertEquals(first.getState(), second.getState());
                assertEquals(first.getZipCode(), second.getZipCode());
            });
            index += 1;
            result = ar.findAllAddresses(result.nextOrLastPageable());

        }

    }

    @TestFactory
    @Order(2)
    @DisplayName("Find all Addresses with query and pageable compare to memory list")
    Stream<DynamicTest> findAllAddressesWithQueryAndPageableAndComparateToMemoryList_whenProvidedListOfAddresses_returnListPageableListOfAddresses() {
        var initialPageNumber = 0;

        var initialPageSize = 4;

        Pageable pageable = PageRequest.of(initialPageNumber, initialPageSize);

        var result = ar.findAllAddresses(pageable);

        var sortedMapList = getSortedMapList(initialPageNumber, initialPageSize);

        var it = new AddressIterable(result, ar);


        var step = StreamSupport.stream(it.spliterator(), false);

        AtomicInteger index = new AtomicInteger();

        return step.flatMap((address) -> {
            Supplier<Stream<Address>> s = address::stream;

            var stepOne = s.get().map((aa) -> dynamicTest(
                    format("Type of id:%s Type of street:%s Type of city:%s Type of state:%s Type of zipCode:%s",
                            aa.getId().getClass().getName(),
                            aa.getStreet().getClass().getName(),
                            aa.getCity().getClass().getName(),
                            aa.getState().getClass().getName(),
                            aa.getZipCode().getClass().getName()
                    ),
                    () -> {
                        assertInstanceOf(Long.class, aa.getId());
                        assertInstanceOf(String.class, aa.getStreet());
                        assertInstanceOf(String.class, aa.getCity());
                        assertInstanceOf(String.class, aa.getState());
                        assertInstanceOf(Integer.class, aa.getZipCode());
                    }
            ));

            var stepTwo = StreamUtils.zip(s.get(), sortedMapList.get(index.get()).stream(), Pair::of)
                    .map(pv -> dynamicTest(
                            format("First id:%d street:%s  city:%s  state:%s  zipCode:%s Second id:%d street:%s  city:%s  state:%s  zipCode:%s  ",
                                    pv.getFirst().getId(),
                                    pv.getFirst().getStreet(),
                                    pv.getFirst().getCity(),
                                    pv.getFirst().getState(),
                                    pv.getFirst().getZipCode(),

                                    pv.getSecond().getId(),
                                    pv.getSecond().getStreet(),
                                    pv.getSecond().getCity(),
                                    pv.getSecond().getState(),
                                    pv.getSecond().getZipCode()
                            ),
                            () -> {
                                var first = pv.getFirst();
                                var second = pv.getSecond();
                                assertEquals(first.getId(), second.getId());
                                assertEquals(first.getStreet(), second.getStreet());
                                assertEquals(first.getCity(), second.getCity());
                                assertEquals(first.getState(), second.getState());
                                assertEquals(first.getZipCode(), second.getZipCode());
                            })

                    );
            index.addAndGet(1);
            return Stream.concat(stepOne, stepTwo);
        });
    }


    private Map<Integer, List<Address>> getSortedMapList(int startInclusive, int initialPageSize) {
        return IntStream.range(startInclusive, this.inputAddresses.size()).boxed()
                .collect(Collectors.groupingBy(i -> i / initialPageSize,
                        Collectors.mapping(i -> this.inputAddresses.get(i), Collectors.toList())));
    }

    private List<Address> getResource() throws IOException {
        var sources = resourceLoader.getResource("classpath:/addresses.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(sources.getFile().toURI()), new TypeReference<List<Address>>() {
        });
    }

}
