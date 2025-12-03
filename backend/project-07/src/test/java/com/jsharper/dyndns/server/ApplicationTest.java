package com.jsharper.dyndns.server;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    void contextLoads(){
        var product = new ProductEntity("Test", "test desc", 15.5);
        productRepository.save(product);
        Assertions.assertEquals("test", "test");
    }
}
