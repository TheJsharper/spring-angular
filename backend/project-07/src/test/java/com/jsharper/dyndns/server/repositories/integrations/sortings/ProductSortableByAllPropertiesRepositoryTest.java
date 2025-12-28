package com.jsharper.dyndns.server.repositories.integrations.sortings;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductPageableAndSortableRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
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
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductSortableByAllPropertiesRepositoryTest {

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
    @DisplayName("find all sorted product by name  if provided sorting configuration as parameter return sorted iterable products")
    Stream<DynamicTest> findAllSortingByName_whenProvidedSortingConfiguration_returnSortedIterable() {

        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        this.inputProducts.sort(Comparator.comparing(ProductEntity::getName));

        var result = er.findAll(sort);

        Supplier<Stream<ProductEntity>> supplier = () -> StreamSupport.stream(result.spliterator(), false);

        var stepOne = supplier.get().map((product ->
                DynamicTest.dynamicTest(
                        String.format("Type of Id:%s Type of Name:%s Type of Price:%s Type of desc:%s",
                                product.getId().getClass().getName(), product.getName().getClass().getName(), product.getPrice().getClass().getName(), product.getDesc().getClass().getName()),
                        () -> {
                            Assertions.assertInstanceOf(Long.class, product.getId());
                            Assertions.assertInstanceOf(String.class, product.getName());
                            Assertions.assertInstanceOf(String.class, product.getDesc());
                            Assertions.assertInstanceOf(Double.class, product.getPrice());
                        }
                )));


        var pairs = StreamUtils.zip(inputProducts.stream(), supplier.get(), Pair::of);

        var stepTwo = pairs.map(p -> DynamicTest.dynamicTest(
                String.format("First Static List of Id:%s  Name:%s  Price:%f Desc:%s\n Second of Id:%s  Name:%s  Price:%f Desc:%s",
                        p.getFirst().getId(), p.getFirst().getName(), p.getFirst().getPrice(), p.getFirst().getDesc(),
                        p.getSecond().getId(), p.getSecond().getName(), p.getSecond().getPrice(), p.getSecond().getDesc()
                ),
                () -> {
                    var first = p.getFirst();
                    var second = p.getSecond();
                    Assertions.assertEquals(first.getId(), second.getId());
                    Assertions.assertEquals(first.getName(), second.getName());
                    Assertions.assertEquals(first.getDesc(), second.getDesc());
                    Assertions.assertEquals(first.getPrice(), second.getPrice());
                }

        ));
        return Stream.concat(stepOne, stepTwo);
    }

    @TestFactory
    @Order(2)
    @DisplayName("warning up find all sorting by desc if provided sorting configuration as parameter return  sorted iterable products")
    Stream<DynamicTest> findAllSortingByDesc_whenProvidedAndSortingConfiguration_returnSortedIterable() {

        Sort sort = Sort.by(Sort.Direction.ASC, "desc");

        this.inputProducts.sort(Comparator.comparing(ProductEntity::getDesc));

        var result = er.findAll(sort);

        Supplier<Stream<ProductEntity>> supplier = () -> StreamSupport.stream(result.spliterator(), false);

        var stepOne = supplier.get().map((product ->
                DynamicTest.dynamicTest(
                        String.format("Type of Id:%s Type of Name:%s Type of Price:%s Type of desc:%s",
                                product.getId().getClass().getName(), product.getName().getClass().getName(), product.getPrice().getClass().getName(), product.getDesc().getClass().getName()),
                        () -> {
                            Assertions.assertInstanceOf(Long.class, product.getId());
                            Assertions.assertInstanceOf(String.class, product.getName());
                            Assertions.assertInstanceOf(String.class, product.getDesc());
                            Assertions.assertInstanceOf(Double.class, product.getPrice());
                        }
                )));


        var pairs = StreamUtils.zip(inputProducts.stream(), supplier.get(), Pair::of);

        var stepTwo = pairs.map(p -> DynamicTest.dynamicTest(
                String.format("First Static List of Id:%s  Name:%s  Price:%f Desc:%s\n Second of Id:%s  Name:%s  Price:%f Desc:%s",
                        p.getFirst().getId(), p.getFirst().getName(), p.getFirst().getPrice(), p.getFirst().getDesc(),
                        p.getSecond().getId(), p.getSecond().getName(), p.getSecond().getPrice(), p.getSecond().getDesc()
                ),
                () -> {
                    var first = p.getFirst();
                    var second = p.getSecond();
                    Assertions.assertEquals(first.getId(), second.getId());
                    Assertions.assertEquals(first.getName(), second.getName());
                    Assertions.assertEquals(first.getDesc(), second.getDesc());
                    Assertions.assertEquals(first.getPrice(), second.getPrice());
                }

        ));
        return Stream.concat(stepOne, stepTwo);

    }

    @TestFactory
    @Order(3)
    @DisplayName("find all sorting by price if provided sorting configuration as parameter return and sorted by price iterable products")
    Stream<DynamicTest> findAllSortingByPrice_whenProvidedSortingConfiguration_returnSortedByPriceIterable() {

        Sort sort = Sort.by(Sort.Direction.ASC, "price");

        this.inputProducts.sort(Comparator.comparing(ProductEntity::getPrice));

        var result = er.findAll(sort);

        Supplier<Stream<ProductEntity>> supplier = () -> StreamSupport.stream(result.spliterator(), false);

        var stepOne = supplier.get().map((product ->
                DynamicTest.dynamicTest(
                        String.format("Type of Id:%s Type of Name:%s Type of Price:%s Type of desc:%s",
                                product.getId().getClass().getName(), product.getName().getClass().getName(), product.getPrice().getClass().getName(), product.getDesc().getClass().getName()),
                        () -> {
                            Assertions.assertInstanceOf(Long.class, product.getId());
                            Assertions.assertInstanceOf(String.class, product.getName());
                            Assertions.assertInstanceOf(String.class, product.getDesc());
                            Assertions.assertInstanceOf(Double.class, product.getPrice());
                        }
                )));


        var pairs = StreamUtils.zip(inputProducts.stream(), supplier.get(), Pair::of);

        var stepTwo = pairs.map(p -> DynamicTest.dynamicTest(
                String.format("First Static List of Id:%s  Name:%s  Price:%f Desc:%s\n Second of Id:%s  Name:%s  Price:%f Desc:%s",
                        p.getFirst().getId(), p.getFirst().getName(), p.getFirst().getPrice(), p.getFirst().getDesc(),
                        p.getSecond().getId(), p.getSecond().getName(), p.getSecond().getPrice(), p.getSecond().getDesc()
                ),
                () -> {
                    var first = p.getFirst();
                    var second = p.getSecond();
                    Assertions.assertEquals(first.getId(), second.getId());
                    Assertions.assertEquals(first.getName(), second.getName());
                    Assertions.assertEquals(first.getDesc(), second.getDesc());
                    Assertions.assertEquals(first.getPrice(), second.getPrice());
                }

        ));
        return Stream.concat(stepOne, stepTwo);
    }


    private List<ProductEntity> getResource() throws IOException {
        var sources = resourceLoader.getResource("classpath:/products.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(sources.getFile().toURI()), new TypeReference<List<ProductEntity>>() {
        });
    }

}