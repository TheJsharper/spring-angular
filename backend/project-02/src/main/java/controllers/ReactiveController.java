package controllers;

import helpers.Helper;
import lombok.Builder;
import lombok.Value;
import models.Product;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("reactive")
public class ReactiveController {

    private final RestClient restClient = RestClient.builder().build();


    private static  final Logger logger= org.slf4j.LoggerFactory
            .getLogger(ReactiveController.class);
    @GetMapping("/products")
    public Flux<Product> getAll (){
        return Flux.fromIterable(Helper.getDummyProducts());
    }


    @GetMapping(value = "line/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getAllLineByLine(){
    logger.info("Calling Stream Method");
        return Flux.fromIterable(Helper.getDummyProducts())
                .delayElements(Duration.ofSeconds(1))
                .doOnNext( (p)-> ReactiveController.logger.info("Products next "+ p));

    }

    @GetMapping(value = "sse/data-entry", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DataEntry> getDataEntryByServerSideEvent(){
        return stream();
    }

    @GetMapping(value = "ndjson/data-entry", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DataEntry> getDataEntryByNdJson(){
        return stream();
    }

    private Flux<DataEntry> stream(){
        return Flux.range(1, 100)
                .delayElements(Duration.ofSeconds(1))
                .map((value)-> new DataEntry(value, Instant.now()));
    }

    @Value
    @Builder
   public static class DataEntry{
        long index;
        Instant timeInstant;
    }
}
