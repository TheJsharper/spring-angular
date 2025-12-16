package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductFinderRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductFinderRepositoryTest {

    @Autowired
    private ProductFinderRepository er;

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


    @TestFactory
    @Order(1)
    @DisplayName("find by product name  if provided Name return found by product Name")
    Stream<DynamicTest> findByProductName_whenProvidedProductName_returnListOfProductFoundByProductName() {
        var name = "Electric Heat Gun";

        var result = er.findByName(name);

        var sizeTest = DynamicTest.dynamicTest(String.format("Result size %d", result.size()), () -> Assertions.assertFalse(result.isEmpty()));

        var stepOne = Stream.of(sizeTest);

        var stepTwo = result.stream().map(
                (p) -> DynamicTest.dynamicTest(
                        String.format("Parameter Name %s return name value %s", name, p.getName()), () -> Assertions.assertEquals(name, p.getName())
                )
        );
        return Stream.concat(stepOne, stepTwo);
    }


    @TestFactory
    @Order(2)
    @DisplayName("find by product name and description  if provided name and description return found by product Name")
    Stream<DynamicTest> findByProductNameAndDesc_whenProvidedProductNameAndDesc_returnListOfProductFoundByProductNameAndDesc() {
        var name = "Electric Heat Gun";

        var desc = "Versatile 1800W heat gun for paint removal, shrink wrapping, and bending plastics.";

        var result = er.findByNameAndDesc(name, desc);

        var sizeTest = DynamicTest.dynamicTest(String.format("Result size %d", result.size()), () -> Assertions.assertFalse(result.isEmpty()));

        var stepOne = Stream.of(sizeTest);

        var stepTwo = result.stream().map(
                (p) -> DynamicTest.dynamicTest(
                        String.format("Parameter Name %s return name value %s", name, p.getName()),
                        () -> {
                            Assertions.assertEquals(name, p.getName());
                            Assertions.assertEquals(desc, p.getDesc());
                        }
                )
        );
        return Stream.concat(stepOne, stepTwo);
    }

    @TestFactory
    @Order(3)
    @DisplayName("find by product price greater than  if provided price product return found by product Price greater than")
    Stream<DynamicTest> findByProductPriceGreaterThan_whenProvidedProductPriceGreaterThan_returnListOfProductFoundByProductPriceGreaterThan() {

        var price = 98.00d;

        var result = er.findByPriceGreaterThan(price);

        var sizeTest = DynamicTest.dynamicTest(String.format("Result size %d", result.size()), () -> Assertions.assertFalse(result.isEmpty()));

        var stepOne = Stream.of(sizeTest);

        var stepTwo = result.stream().map(
                (p) -> DynamicTest.dynamicTest(
                        String.format("Parameter Price %f return price value %f", price, p.getPrice()),
                        () -> Assertions.assertTrue(price < p.getPrice())

                )
        );
        return Stream.concat(stepOne, stepTwo);
    }


    @TestFactory
    @Order(4)
    @DisplayName("find by product description contains than  if provided description contains return found by product description contains")
    Stream<DynamicTest> findByProductDescContains_whenProvidedProductDescContains_returnListOfProductFoundByProductDescContains() {

        var desc = "tool";

        var result = er.findByDescContains(desc);

        var sizeTest = DynamicTest.dynamicTest(String.format("Result size %d", result.size()), () -> Assertions.assertFalse(result.isEmpty()));

        var stepOne = Stream.of(sizeTest);

        var stepTwo = result.stream().map(
                (p) -> DynamicTest.dynamicTest(
                        String.format("Parameter Price %s return price value %s", desc, p.getDesc()),
                        () -> Assertions.assertTrue(p.getDesc().contains(desc))

                )
        );
        return Stream.concat(stepOne, stepTwo);
    }

    @TestFactory
    @Order(5)
    @DisplayName("find by product between prices if provided product between prices return found by product between prices")
    Stream<DynamicTest> findByProductBetweenPrices_whenProvidedProductBetweenPrices_returnListOfProductFoundByPriceBetween() {

        var price1 = 50.00d;

        var price2 = 100.00d;

        var result = er.findByPriceBetween(price1, price2);

        var sizeTest = DynamicTest.dynamicTest(String.format("Result size %d", result.size()), () -> Assertions.assertFalse(result.isEmpty()));

        var stepOne = Stream.of(sizeTest);

        var stepTwo = result.stream().map(
                (p) -> DynamicTest.dynamicTest(
                        String.format("Parameter between Price1 %f and Price2  %f return price value %f", price1, price2, p.getPrice()),
                        () -> Assertions.assertTrue(p.getPrice() >= price1 && p.getPrice() <= price2)

                )
        );
        return Stream.concat(stepOne, stepTwo);
    }


    @TestFactory
    @Order(6)
    @DisplayName("find by product description like than  if provided description like return found by product description like")
    Stream<DynamicTest> findByProductDescLike_whenProvidedProductDescLikeContains_returnListOfProductFoundByProductDescLike() {

        var desc = "voltage";

        var result = er.findByDescLike("%" + desc + "%");

        var sizeTest = DynamicTest.dynamicTest(String.format("Result size %d", result.size()), () -> Assertions.assertFalse(result.isEmpty()));

        var stepOne = Stream.of(sizeTest);

        var stepTwo = result.stream().map(
                (p) -> DynamicTest.dynamicTest(
                        String.format("Parameter Price %s return price value %s", desc, p.getDesc()),
                        () -> Assertions.assertTrue(p.getDesc().contains(desc))

                )
        );
        return Stream.concat(stepOne, stepTwo);
    }


    @TestFactory
    @Order(7)
    @DisplayName("find by product by ids if provided list of ids prices return found by ids")
    Stream<DynamicTest> findByProductByIds_whenProvidedProductListOfIds_returnListOfProductFoundByIds() {


        var ids = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        var result = er.findByIdIn(ids);

        var sizeTest = DynamicTest.dynamicTest(String.format("Result size %d", result.size()), () -> Assertions.assertFalse(result.isEmpty()));

        var stepOne = Stream.of(sizeTest);

        var resultIds = result.stream().map(ProductEntity::getId).toArray(Long[]::new);

        var testIds = ids.toArray(Long[]::new);

        var pairs = StreamUtils.zip(Arrays.stream(testIds), Arrays.stream(resultIds), Pair::of);

        var formatted = pairs.map((p) -> String.format(" Parameter id = %d value id = %d", p.getFirst(), p.getSecond())).collect(Collectors.joining("\n"));


        var stepTwo = Stream.of(DynamicTest.dynamicTest(formatted, () -> Assertions.assertArrayEquals(testIds, resultIds)));

        return Stream.concat(stepOne, stepTwo);
    }

    private List<ProductEntity> getResource() throws IOException {
        var sources = resourceLoader.getResource("classpath:/products.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(sources.getFile().toURI()), new TypeReference<List<ProductEntity>>() {
        });
    }


    private Executable assertEqualProductEntity(Pair<ProductEntity, ProductEntity> p) {
        return () -> {
            assertEquals(p.getFirst().getName(), p.getSecond().getName());
            assertEquals(p.getFirst().getDesc(), p.getSecond().getDesc());
            assertEquals(p.getFirst().getPrice(), p.getSecond().getPrice());
        };
    }

    private String getEqualTwoProductEntities(Pair<ProductEntity, ProductEntity> p) {
        var breakLine = System.lineSeparator();
        return format("First Name %s second Name %s" + breakLine
                        + " First description %s Second Description %s" + breakLine
                        + "First price %f Second price %f",
                p.getFirst().getName(), p.getSecond().getName(),
                p.getFirst().getDesc(), p.getSecond().getDesc(),
                p.getFirst().getPrice(), p.getSecond().getPrice()
        );
    }
}
