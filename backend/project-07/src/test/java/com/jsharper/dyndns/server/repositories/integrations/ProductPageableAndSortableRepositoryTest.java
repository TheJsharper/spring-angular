package com.jsharper.dyndns.server.repositories.integrations;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductPageableAndSortableRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductPageableAndSortableRepositoryTest {

    @Autowired
    public ProductPageableAndSortableRepository er;


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


    @Autowired
    private ResourceLoader resourceLoader;


    /*@TestFactory*/
    @Test
    @Order(1)
    @DisplayName("find all paging and sorting if provided paging and sorting configuration as parameter return paged and sorted iterable products")
    /*Stream<DynamicTest>*/ void findAllPaging_whenProvidedPagingAndSortingConfiguration_returnPagedAndSortedIterable() {

        Pageable p = PageRequest.of(0, 4);
        var result = er.findAll(p);
       /* var s = StreamSupport.stream(result.spliterator(), false);
        while (result.hasNext()) {
            result.forEach(System.out::println);
            result = er.findAll(result.nextOrLastPageable());
            System.out.printf("\n next Page---------------------\n Total Pages:%d\n PageNumber:%d\n Page Size:%d\n"
                    , result.getTotalPages(), result.getPageable().getPageNumber(), result.getPageable().getPageSize());
        }*/
        //result.forEach(System.out::println);

        var it = new it(result, this.er);
        var iterable = this.getIterablePage(it);
        var step = StreamSupport.stream(iterable.spliterator(), false);
        step.forEach(pp -> {

            pp.forEach(System.out::println);
            System.out.printf("\n next Page---------------------\n Total Pages:%d\n PageNumber:%d\n Page Size:%d\n"
                    , pp.getTotalPages(), pp.getPageable().getPageNumber(), pp.getPageable().getPageSize());
        });

    }

    private Iterable<Page> getIterablePage(Iterator<Page> iterator) {
        return () -> iterator;
    }

    private List<ProductEntity> getResource() throws IOException {
        var sources = resourceLoader.getResource("classpath:/products.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(sources.getFile().toURI()), new TypeReference<List<ProductEntity>>() {
        });
    }

}

class it implements Iterator<Page> {
    private Page page;
    private ProductPageableAndSortableRepository er;

    public it(Page page, ProductPageableAndSortableRepository er) {
        this.page = page;
        this.er = er;
    }

    @Override
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    public Page next() {
        return er.findAll(this.page.nextOrLastPageable());
    }
}