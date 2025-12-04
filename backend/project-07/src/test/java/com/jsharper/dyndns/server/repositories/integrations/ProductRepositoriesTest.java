package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoriesTest {

    @Autowired
    private ProductRepository productRepository;

    private ProductEntity storedProduct;

    private Supplier<Stream<ProductEntity>> storeAllProductEntity;


    @TestFactory
    @Order(1)
    @DisplayName("create with saveAll(iterable) returns stored list ")
    Stream<DynamicTest> testIterateProduct_whenGetsArguments_returnsDynamicTest() {
        Iterable<ProductEntity> iterator = getArguments().toList();

        Supplier<Stream<ProductEntity>> storedIterator = () -> this.productRepository.saveAll(iterator).stream();

        this.storeAllProductEntity = storedIterator;

        var stream = StreamSupport.stream(iterator.spliterator(), false);

        var pairs = StreamUtils.zip(storedIterator.get(), stream, Pair::of);

        var breakLine = System.lineSeparator();

        return pairs.map(p ->
                DynamicTest.dynamicTest(String.format("First Name %s second Name %s" + breakLine
                                        + " First description %s Second Description %s" + breakLine
                                        + "First price %f Second price %f",
                                p.getFirst().getName(), p.getSecond().getName(),
                                p.getFirst().getDesc(), p.getSecond().getDesc(),
                                p.getFirst().getPrice(), p.getSecond().getPrice()
                        ),
                        () -> {
                            assertEquals(p.getFirst().getName(), p.getSecond().getName());
                            assertEquals(p.getFirst().getDesc(), p.getSecond().getDesc());
                            assertEquals(p.getFirst().getPrice(), p.getSecond().getPrice());
                        }
                ));

    }

    private Stream<ProductEntity> getArguments() {
        var random = new Random();
        return IntStream
                .iterate(0, (next) -> next + 2)
                .limit(100)
                .mapToObj(next -> new ProductEntity("Product" + next, "Product Description " + next, random.nextDouble(10.0, 500.00)));
    }

    @TestFactory
    @Order(2)
    @DisplayName("Find all stored entities in db")
    Stream<DynamicTest> testFindAll_whenCallFindAllWithParameters_returnAllExistedEntitiesInDB() {
        var stream = productRepository.findAll().stream();

        var pairs = StreamUtils.zip(this.storeAllProductEntity.get(), stream, Pair::of);

        var breakLine = System.lineSeparator();

        return pairs.map(p ->
                DynamicTest.dynamicTest(String.format("First Name %s second Name %s" + breakLine
                                        + " First description %s Second Description %s" + breakLine
                                        + "First price %f Second price %f",
                                p.getFirst().getName(), p.getSecond().getName(),
                                p.getFirst().getDesc(), p.getSecond().getDesc(),
                                p.getFirst().getPrice(), p.getSecond().getPrice()
                        ),
                        () -> {
                            assertEquals(p.getFirst().getName(), p.getSecond().getName());
                            assertEquals(p.getFirst().getDesc(), p.getSecond().getDesc());
                            assertEquals(p.getFirst().getPrice(), p.getSecond().getPrice());
                        }
                ));
    }

    @Order(3)
    @TestFactory
    @DisplayName("find all by ids if provided valid ids return list of entities")
    Stream<DynamicTest> findAllById_whenProvidedValidIds_ReturnIterableProductEntities() {
        var productEntityIds = this.storeAllProductEntity.get().map(ProductEntity::getId).toList();

        var returnValuesProductEntities = this.productRepository.findAllById(productEntityIds).stream();

        var pairs = StreamUtils.zip(productEntityIds.stream(), returnValuesProductEntities, Pair::of);

        return pairs.map(p ->
                DynamicTest.dynamicTest(String.format("id %d equals id %d", p.getFirst(), p.getSecond().getId()),
                        () -> assertEquals(p.getFirst(), p.getSecond().getId())));
    }

    @Test
    @Order(4)
    @DisplayName("Create Product and test return object with it")
    void createProduct_whenValidObject_returnsStoredObject() {
        //Arrange
        var product = new ProductEntity("Test", "test desc", 15.5);

        //Act
        var storeProduct = productRepository.save(product);
        this.storedProduct = storeProduct;

        //Assert
        assertEquals(product.getName(), storeProduct.getName());
        assertEquals(product.getDesc(), storeProduct.getDesc());
        assertEquals(product.getPrice(), storeProduct.getPrice());
        assertNotNull(storeProduct.getId());
    }

    @Test
    @Order(5)
    @DisplayName("Find by Product id and test with stored Product")
    void createProduct_whenValidObject_returnsFindById() {
        //Arrange

        //Act
        var storedProduct = productRepository.findById(this.storedProduct.getId()).orElseThrow();

        //Assert
        assertNotNull(storedProduct.getName());
        assertNotNull(storedProduct.getDesc());
        assertNotNull(storedProduct.getPrice());
        assertNotNull(storedProduct.getId());

        assertEquals(storedProduct.getId(), this.storedProduct.getId());
        assertEquals(storedProduct.getName(), this.storedProduct.getName());
        assertEquals(storedProduct.getDesc(), this.storedProduct.getDesc());
        assertEquals(storedProduct.getPrice(), this.storedProduct.getPrice());
    }

    @Order(6)
    @TestFactory
    @DisplayName("exists ProductEntity if provided valid id")
    Stream<DynamicTest> existsByIdFromStoredList_whenProvidedProductEntityId_ReturnExistedBoolean() {
        return storeAllProductEntity.get().map(ProductEntity::getId)
                .map(id -> DynamicTest.dynamicTest(String.format(" ProductEntity id %d", id), () -> assertTrue(productRepository.existsById(id))));
    }

}