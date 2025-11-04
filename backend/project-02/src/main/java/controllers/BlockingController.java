package controllers;

import helpers.Helper;
import models.Product;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("blocking")
public class BlockingController {

    private static  final Logger logger= org.slf4j.LoggerFactory
            .getLogger(BlockingController.class);

    private final RestClient restClient = RestClient.builder().build();


    @GetMapping("products")
    public List<Product> getProducts(){
        return Helper.getDummyProducts();
    }

}
