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
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoriesTest {

    @Autowired
    private ProductRepository productRepository;

    private ProductEntity storedProduct;

    @Test
    @Order(1)
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
    @Order(2)
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

    @TestFactory
    @Order(3)
    @DisplayName("create with saveAll(iterable) returns stored list ")
    Stream<DynamicTest> testIterateProduct_whenGetsArguments_returnsDynamicTest() {
        Iterable<ProductEntity> iterator = getArguments().toList();

        var storedIterator = this.productRepository.saveAll(iterator).stream();

        var stream = StreamSupport.stream(iterator.spliterator(), false);

        var pairs = StreamUtils.zip(storedIterator, stream, Pair::of);

        return pairs.map(p -> DynamicTest.dynamicTest(String.format("First Name %s Secund Name %s", p.getFirst().getName(), p.getSecond().getName()), () -> assertEquals(p.getFirst().getName(), p.getSecond().getName())));

    }

    private Stream<ProductEntity> getArguments() {
        var random = new Random();
        return IntStream
                .iterate(0, (next) -> next + 2)
                .limit(100)
                .mapToObj(next -> new ProductEntity("Product" + next, "Product Description " + next, random.nextDouble(10.0, 500.00)));
    }

}
//record testProduct()