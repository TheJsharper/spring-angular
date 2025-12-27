package com.jsharper.dyndns.server.repositories.integrations.sortings;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductPageableAndSortableRepository;
import com.jsharper.dyndns.server.repositories.integrations.iterables.ProductIterable;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductPageableAndSortableByDescAndThenPriceRepositoryTest {

    @Autowired
    private ProductPageableAndSortableRepository er;

    @Autowired
    private ResourceLoader resourceLoader;


    private List<ProductEntity> inputProducts;

    @BeforeAll
    public void init() throws IOException {

        inputProducts = this.getResource();

        Assertions.assertNotNull(inputProducts);

        Supplier<Stream<ProductEntity>> products = () -> StreamSupport.stream(er.saveAll(inputProducts).spliterator(), false);

        Assertions.assertTrue(products.get().findAny().isPresent());
    }

    @AfterAll
    public void cleanUp() {
        er.deleteAll();

        Assertions.assertEquals(0, er.count());
    }


    @TestFactory
    @Order(1)
    @DisplayName("find all paging  if provided paging configuration as parameter return paged iterable products")
    Stream<DynamicTest> findAllPaging_whenProvidedPagingAndSortingConfiguration_returnPagedAndSortedIterable() {

        Pageable p = PageRequest.of(0, 4);

        var result = er.findAll(p);

        var it = new ProductIterable(result, this.er);

        var step = StreamSupport.stream(it.spliterator(), false);

        return step.flatMap((product -> {
            var stepOne = product.map((pp) -> DynamicTest.dynamicTest(
                    String.format("Type of Id:%s Type of Name:%s Type of Price:%s Type of desc:%s",
                            pp.getId().getClass().getName(), pp.getName().getClass().getName(), pp.getPrice().getClass().getName(), pp.getDesc().getClass().getName()),
                    () -> {
                        Assertions.assertInstanceOf(Long.class, pp.getId());
                        Assertions.assertInstanceOf(String.class, pp.getName());
                        Assertions.assertInstanceOf(String.class, pp.getDesc());
                        Assertions.assertInstanceOf(Double.class, pp.getPrice());
                    }
            )).stream().toList().stream();

            var stepTwo = Stream.of(DynamicTest.dynamicTest(
                    String.format("\n next Page---------------------\n Total Pages:%d\n PageNumber:%d\n Page Size:%d\n TotalSize:%d\n PageNumber: %d\n pageSize: %d\n"
                            , product.getTotalPages(), product.getPageable().getPageNumber(), product.getPageable().getPageSize(),
                            it.getTotalPages(), it.getPageNumber(), it.getPageSize()),
                    () -> {

                        Assertions.assertEquals(product.getPageable().getPageSize(), product.getSize());
                        Assertions.assertEquals(it.getTotalPages(), product.getTotalPages());
                        Assertions.assertEquals(it.getPageNumber(), product.getPageable().getPageNumber());
                        Assertions.assertEquals(it.getPageSize(), product.getPageable().getPageSize());
                    }
            ));
            return Stream.concat(stepOne, stepTwo);
        }));

    }

    @Test
    @Order(2)
    @DisplayName("warning up find all paging and sorting if provided paging and sorting configuration as parameter return paged and sorted iterable products")
    void warningUpFindAllPagingAndSorting_whenProvidedPagingAndSortingConfiguration_returnPagedAndSortedIterable() {
        var initialPageNumber = 0;

        var initialPageSize = 4;

        Sort sort = Sort.by(Sort.Direction.ASC,  "desc", "price");

        Pageable p = PageRequest.of(initialPageNumber, initialPageSize, sort);

        var result = er.findAll(p);

        this.inputProducts.sort(
                Comparator.comparing(ProductEntity::getDesc)
                        .thenComparing(ProductEntity::getPrice)
        );

        var sortedMapList = getSortedMapList(initialPageNumber, initialPageSize);

        int index = 0;

        while (result.hasNext()) {

            System.out.println(result.getTotalPages() + "\n " + result.getSize() + "\n " + result.getNumber());

            Page<ProductEntity> finalResult = result;

            Supplier<Stream<ProductEntity>> actualProductEntity = finalResult;

            var pairs = StreamUtils.zip(sortedMapList.get(index).stream(), actualProductEntity.get(), Pair::of);

            pairs.forEach(pair -> {
                var first = pair.getFirst();
                var second = pair.getSecond();
                Assertions.assertEquals(first.getId(), second.getId());
                Assertions.assertEquals(first.getName(), second.getName());
                Assertions.assertEquals(first.getDesc(), second.getDesc());
                Assertions.assertEquals(first.getPrice(), second.getPrice());
            });
            index += 1;
            result = er.findAll(result.nextOrLastPageable());

        }

    }

    @TestFactory
    @Order(3)
    @DisplayName("find all paging and sorting if provided paging and sorting configuration as parameter return paged and sorted iterable products")
    Stream<DynamicTest> findAllPagingAndSorting_whenProvidedPagingAndSortingConfiguration_returnPagedAndSortedIterable() {

        var initialPageNumber = 0;

        var initialPageSize = 4;

        Sort sort = Sort.by(Sort.Direction.ASC,  "desc", "price");

        Pageable p = PageRequest.of(initialPageNumber, initialPageSize, sort);

        var result = er.findAll(p);

        this.inputProducts.sort(
                Comparator.comparing(ProductEntity::getDesc)
                        .thenComparing(ProductEntity::getPrice)
        );

        var sortedMapList = getSortedMapList(0, 4);

        var it = new ProductIterable(result, this.er);

        var step = StreamSupport.stream(it.spliterator(), false);

        AtomicInteger index = new AtomicInteger();
        return step.flatMap((product -> {

            Supplier<Stream<ProductEntity>> s = product::stream;


            var stepOne = s.get().map((pp) -> DynamicTest.dynamicTest(
                    String.format("Type of Id:%s Type of Name:%s Type of Price:%s Type of desc:%s",
                            pp.getId().getClass().getName(), pp.getName().getClass().getName(), pp.getPrice().getClass().getName(), pp.getDesc().getClass().getName()),
                    () -> {
                        Assertions.assertInstanceOf(Long.class, pp.getId());
                        Assertions.assertInstanceOf(String.class, pp.getName());
                        Assertions.assertInstanceOf(String.class, pp.getDesc());
                        Assertions.assertInstanceOf(Double.class, pp.getPrice());
                    }
            ));

            var stepTwo = StreamUtils.zip(s.get(), sortedMapList.get(index.get()).stream(), Pair::of).map(pv -> DynamicTest.dynamicTest(
                    String.format("ProductIdentity sorted id= %d == id=%d and name %s == name %s and  desc %s == desc %s and price %f == price %f ",
                            pv.getFirst().getId(), pv.getSecond().getId(),
                            pv.getFirst().getName(), pv.getSecond().getName(),
                            pv.getFirst().getDesc(), pv.getSecond().getDesc(),
                            pv.getFirst().getPrice(), pv.getSecond().getPrice()
                    ),
                    () -> {
                        Assertions.assertEquals(pv.getFirst().getId(), pv.getSecond().getId());
                        Assertions.assertEquals(pv.getFirst().getName(), pv.getSecond().getName());
                        Assertions.assertEquals(pv.getFirst().getDesc(), pv.getSecond().getDesc());
                        Assertions.assertEquals(pv.getFirst().getPrice(), pv.getSecond().getPrice());
                    }
            ));


            var stepThird = Stream.of(DynamicTest.dynamicTest(
                    String.format("\n next Page---------------------\n Total Pages:%d\n PageNumber:%d\n Page Size:%d\n TotalSize:%d\n PageNumber: %d\n pageSize: %d\n"
                            , product.getTotalPages(), product.getPageable().getPageNumber(), product.getPageable().getPageSize(),
                            it.getTotalPages(), it.getPageNumber(), it.getPageSize()),
                    () -> {

                        Assertions.assertEquals(product.getPageable().getPageSize(), product.getSize());
                        Assertions.assertEquals(it.getTotalPages(), product.getTotalPages());
                        Assertions.assertEquals(it.getPageNumber(), product.getPageable().getPageNumber());
                        Assertions.assertEquals(it.getPageSize(), product.getPageable().getPageSize());
                    }
            ));
            index.addAndGet(1);
            return Stream.of(stepOne, stepTwo, stepThird).flatMap((ppp) -> ppp);
        }));

    }

    private Map<Integer, List<ProductEntity>> getSortedMapList(int startInclusive, int initialPageSize) {
        return IntStream.range(startInclusive, this.inputProducts.size()).boxed()
                .collect(Collectors.groupingBy(i -> i / initialPageSize,
                        Collectors.mapping(i -> this.inputProducts.get(i), Collectors.toList())));
    }


    private List<ProductEntity> getResource() throws IOException {
        var sources = resourceLoader.getResource("classpath:/products.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(sources.getFile().toURI()), new TypeReference<List<ProductEntity>>() {
        });
    }

}