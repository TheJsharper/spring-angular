package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
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

    private final int countGeneratedEntities = 100;


    @TestFactory
    @Order(1)
    @DisplayName("create with saveAll(iterable) returns stored list ")
    Stream<DynamicTest> testIterateProduct_whenGetsArguments_returnsDynamicTest() {
        Iterable<ProductEntity> iterator = getArguments().toList();

        Supplier<Stream<ProductEntity>> storedIterator = () -> this.productRepository.saveAll(iterator).stream();

        this.storeAllProductEntity = storedIterator;

        var stream = StreamSupport.stream(iterator.spliterator(), false);

        var pairs = StreamUtils.zip(storedIterator.get(), stream, Pair::of);

        return pairs.map(p -> DynamicTest.dynamicTest(getEqualTwoProductEntities(p), assertEqualProductEntity(p)));

    }

    private Stream<ProductEntity> getArguments() {
        var random = new Random();
        return IntStream
                .iterate(0, (next) -> next + 2)
                .limit(countGeneratedEntities)
                .mapToObj(next -> new ProductEntity("Product" + next, "Product Description " + next, random.nextDouble(10.0, 500.00)));
    }

    @TestFactory
    @Order(2)
    @DisplayName("Find all stored entities in db")
    Stream<DynamicTest> testFindAll_whenCallFindAllWithParameters_returnAllExistedEntitiesInDB() {
        var stream = productRepository.findAll().stream();

        var pairs = StreamUtils.zip(this.storeAllProductEntity.get(), stream, Pair::of);


        return pairs.map(p -> DynamicTest.dynamicTest(getEqualTwoProductEntities(p), assertEqualProductEntity(p)));
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
    @DisplayName("count  entities return store entities")
    void count_whenCallingCountOfEntityManager_returnValueStoredEntities() {
        var count = this.countGeneratedEntities;

        var countOfEntities = this.productRepository.count();

        assertEquals(count, countOfEntities, () -> String.format("expected value %d is not equals stored entity counts %d", count, countOfEntities));
    }

    @Test
    @Order(5)
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
    @Order(6)
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

    @Order(7)
    @TestFactory
    @DisplayName("exists ProductEntity if provided valid id")
    Stream<DynamicTest> existsByIdFromStoredList_whenProvidedProductEntityId_ReturnExistedBoolean() {
        return storeAllProductEntity.get().map(ProductEntity::getId)
                .map(id -> DynamicTest.dynamicTest(String.format(" ProductEntity id %d", id), () -> assertTrue(productRepository.existsById(id))));
    }

    @Order(8)
    @Test
    @DisplayName("delete by id when given id must be valid")
    void deleteById_whenProvidedValidId_verifyFindById() {
        //Arrange

        // Act
        this.productRepository.deleteById(this.storedProduct.getId());

        var exception = assertThrows(NoSuchElementException.class, () -> this.productRepository.findById(this.storedProduct.getId()).orElseThrow());

        //Assert
        assertEquals("No value present", exception.getMessage());

    }

    @Order(9)
    @Test
    @DisplayName("delete by id when given id must be valid")
    void delete_whenProvidedProductEntity_verifyFindById() {
        //Arrange

        var deletingEntity = this.storeAllProductEntity.get().findFirst().orElseThrow();

        // Act
        this.productRepository.delete(deletingEntity);

        var exception = assertThrows(NoSuchElementException.class, () -> this.productRepository.findById(deletingEntity.getId()).orElseThrow());

        //Assert
        assertEquals("No value present", exception.getMessage());

    }

    @Order(10)
    @TestFactory
    @DisplayName("delete by ids when given ids must be valid verify by existsById")
    Stream<DynamicTest> deleteByIds_whenProvidedValidListOfIds_verifyFindById() {
        //Arrange
        Iterable<ProductEntity> iterator = getArguments().toList();

        Supplier<Stream<ProductEntity>> storedIterator = () -> this.productRepository.saveAll(iterator).stream();

        Supplier<Stream<ProductEntity>> stream = () -> StreamSupport.stream(iterator.spliterator(), false);

        var pairs = StreamUtils.zip(storedIterator.get(), stream.get(), Pair::of);

        var stepOneStream = pairs.map(p -> DynamicTest.dynamicTest(getEqualTwoProductEntities(p), assertEqualProductEntity(p)));

        //Act

        this.productRepository.deleteAllById(stream.get().map(ProductEntity::getId).toList());

        // Assert
        var stepTwoStream = stream.get().map(p ->
                DynamicTest.dynamicTest(String.format("ProductEntity Id %d not longer exist in DB", p.getId()),
                        () -> assertFalse(this.productRepository.existsById(p.getId())))
        );
        return Stream.concat(stepOneStream, stepTwoStream);
    }

    @Order(11)
    @TestFactory
    @DisplayName("deleteAll by entities when given ids must be valid verify by existsById")
    Stream<DynamicTest> deleteByEntities_whenProvidedValidListOfIds_verifyFindById() {
        //Arrange
        Iterable<ProductEntity> iterator = getArguments().toList();

        Supplier<Stream<ProductEntity>> storedIterator = () -> this.productRepository.saveAll(iterator).stream();

        Supplier<Stream<ProductEntity>> stream = () -> StreamSupport.stream(iterator.spliterator(), false);

        var pairs = StreamUtils.zip(storedIterator.get(), stream.get(), Pair::of);

        var stepOneStream = pairs.map(p -> DynamicTest.dynamicTest(getEqualTwoProductEntities(p), assertEqualProductEntity(p)));

        //Act

        this.productRepository.deleteAll(stream.get().toList());

        // Assert
        var stepTwoStream = stream.get().map(p ->
                DynamicTest.dynamicTest(String.format("ProductEntity Id %d not longer exist in DB", p.getId()),
                        () -> assertFalse(this.productRepository.existsById(p.getId())))
        );
        return Stream.concat(stepOneStream, stepTwoStream);
    }

    @Order(12)
    @TestFactory
    @DisplayName("deleteAll by entities when given ids must be valid verify by existsById")
    Stream<DynamicTest> deleteByEntity_whenProvidedValidListOfIds_verifyFindById() {

        //Arrange
        var product = new ProductEntity("Test", "test desc", 15.5);

        //Act
        var storeProductLocal = this.productRepository.save(product);

        var pairs = Pair.of(product, storeProductLocal);

        Executable ex = this.assertEqualProductEntity(pairs);

        String message = this.getEqualTwoProductEntities(pairs);

        var stepOne = Stream.of(DynamicTest.dynamicTest(message, ex));

        //Act

        this.productRepository.delete(storeProductLocal);

        // Assert
        var stepTwo = Stream.of(
                DynamicTest.dynamicTest(String.format("ProductEntity Id %d not longer exist in DB", storeProductLocal.getId()),
                        () -> assertFalse(this.productRepository.existsById(storeProductLocal.getId()))));

        return Stream.concat(stepOne, stepTwo);
    }


}