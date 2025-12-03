package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Create Product and test return object with it")
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
}
