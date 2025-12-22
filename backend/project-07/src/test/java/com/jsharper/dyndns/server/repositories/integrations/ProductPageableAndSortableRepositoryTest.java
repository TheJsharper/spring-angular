package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductPageableAndSortableRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.*;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductPageableAndSortableRepositoryTest {

    @Autowired
    private ProductPageableAndSortableRepository er;

    @Autowired
    private ResourceLoader resourceLoader;


    private Supplier<Stream<ProductEntity>> products;

    private List<ProductEntity> inputProducts;

    @BeforeAll
    public void init() throws IOException {

        inputProducts = this.getResource();

        products = () -> StreamSupport.stream(er.saveAll(inputProducts).spliterator(), false);

        Assertions.assertTrue(products.get().findAny().isPresent());
    }

    @AfterAll
    public void cleanUp() {
        er.deleteAll();
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


    @TestFactory
    @Order(2)
    @DisplayName("find all paging and sorting if provided paging and sorting configuration as parameter return paged and sorted iterable products")
    Stream<DynamicTest> findAllPagingAndSorting_whenProvidedPagingAndSortingConfiguration_returnPagedAndSortedIterable() {

        var initialPageNumber = 0;

        var initialPageSize = 4;

        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        Pageable p = PageRequest.of(initialPageNumber, initialPageSize, sort);

        var result = er.findAll(p);

        this.inputProducts.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));

        Supplier<Stream<ProductEntity>>  manuallySorted = ()-> IntStream.range(0, this.inputProducts.size())
                .boxed().collect(Collectors.groupingBy(i -> i / 4,
                        Collectors.mapping(i -> this.inputProducts.get(i), Collectors.toList()
                        ))).values().stream().flatMap(Collection::stream);

        /*manuallySorted.forEach((key, values) -> {

                    System.out.println("Key==>" + key);
                    values.forEach(System.out::println);
                }

        );*/
        var it = new ProductIterable(result, this.er);

        var step = StreamSupport.stream(it.spliterator(), false);

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

            var stepTwo = StreamUtils.zip(s.get(), manuallySorted.get(), Pair::of).map(pv-> DynamicTest.dynamicTest(
                    String.format("ProductIdentity sorted id= %d == id=%d and name %s == name %s and  desc %s == desc %s and price %f == price %f ",
                            pv.getFirst().getId(), pv.getSecond().getId(),
                            pv.getFirst().getName(), pv.getSecond().getName(),
                            pv.getFirst().getDesc(), pv.getSecond().getDesc(),
                            pv.getFirst().getPrice(), pv.getSecond().getPrice()
                    ),
                    ()->{
                       // Assertions.assertEquals(pv.getFirst().getId(), pv.getSecond().getId());
                        Assertions.assertEquals(pv.getFirst().getName(), pv.getSecond().getName());
                      //  Assertions.assertEquals(pv.getFirst().getDesc(), pv.getSecond().getDesc());
                     //   Assertions.assertEquals(pv.getFirst().getPrice(), pv.getSecond().getPrice());
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
            return Stream.of(stepOne, stepTwo, stepThird).flatMap( (ppp)-> ppp);
        }));

    }


    private List<ProductEntity> getResource() throws IOException {
        var sources = resourceLoader.getResource("classpath:/products.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(sources.getFile().toURI()), new TypeReference<List<ProductEntity>>() {
        });
    }

}

class ProductIterable implements Iterator<Page<ProductEntity>>, Iterable<Page<ProductEntity>> {
    private Page<ProductEntity> page;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private ProductPageableAndSortableRepository er;

    public ProductIterable(Page<ProductEntity> page, ProductPageableAndSortableRepository er) {
        this.page = page;
        this.er = er;
        this.totalPages = page.getTotalPages();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public boolean hasNext() {
        //this.totalPages++;
        return page.hasNext();
    }

    @Override
    public Page<ProductEntity> next() {
        var returnValue = this.page;
        this.pageSize = returnValue.getSize();
        this.totalPages = returnValue.getTotalPages();
        this.pageNumber = returnValue.getNumber();
        this.page = er.findAll(this.page.nextOrLastPageable());
        return returnValue;
    }

    @Override
    public Iterator<Page<ProductEntity>> iterator() {
        return this;
    }
}