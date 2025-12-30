package com.jsharper.dyndns.server.repositories.integrations.sortings.addresses;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.data.util.StreamUtils;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressRepositoryNativeQueriesTest extends AddressRepositoryTest {


    @Test
    @DisplayName("using native query sql for extract list of addresses  ")
    @Order(1)
    void testUsingNativeQuerySQLForExtractListOfAddresses_whenProvidedListOfAddresses_returnEntityOfAddresses() {


        var result = ar.findAllAddressesByNativeQuery();


        var pairs = StreamUtils.zip(inputAddresses.stream(), result.stream(), Pair::of);

        pairs.forEach(pair -> {
            var first = pair.getFirst();
            var second = pair.getSecond();
            assertEquals(first.getId(), second.getId());
            assertEquals(first.getStreet(), second.getStreet());
            assertEquals(first.getCity(), second.getCity());
            assertEquals(first.getState(), second.getState());
            assertEquals(first.getZipCode(), second.getZipCode());
        });


    }

    @Test
    @DisplayName("using native query sql for extract list of addresses  ")
    @Order(2)
    void testUsingNativeQuerySQLForExtractListOfAddressesFindByStateName_whenProvidedListOfAddresses_returnEntityOfAddresses() {

        var stateName = "NY";

        var result = ar.findAllAddressesByStateName(stateName);

        var list = inputAddresses.stream().filter(a -> a.getState().equals(stateName)).toList();

        var pairs = StreamUtils.zip(list.stream(), result.stream(), Pair::of);

        pairs.forEach(pair -> {
            var first = pair.getFirst();
            var second = pair.getSecond();
            assertEquals(first.getId(), second.getId());
            assertEquals(first.getStreet(), second.getStreet());
            assertEquals(first.getCity(), second.getCity());
            assertEquals(first.getState(), second.getState());
            assertEquals(first.getZipCode(), second.getZipCode());
        });


    }

    @Test
    @DisplayName("using native query sql for extract list of addresses  ")
    @Order(3)
    void testUsingNativeQuerySQLForExtractListOfAddressesFindByCity_whenProvidedListOfAddresses_returnEntityOfAddresses() {

        var city = "Los Angeles";

        var result = ar.findAllAddressesByCity(city);

        var list = inputAddresses.stream().filter(a -> a.getCity().equals(city)).toList();

        var pairs = StreamUtils.zip(list.stream(), result.stream(), Pair::of);

        pairs.forEach(pair -> {
            var first = pair.getFirst();
            var second = pair.getSecond();
            assertEquals(first.getId(), second.getId());
            assertEquals(first.getStreet(), second.getStreet());
            assertEquals(first.getCity(), second.getCity());
            assertEquals(first.getState(), second.getState());
            assertEquals(first.getZipCode(), second.getZipCode());
        });


    }

    @Test
    @DisplayName("using native query sql for extract list of addresses  ")
    @Order(3)
    void testUsingNativeQuerySQLForExtractListOfAddressesFindContainsStreet_whenProvidedListOfAddresses_returnEntityOfAddresses() {

        var street = "ave";

        var result = ar.findAllAddressesContainsStreetName(street);

        var list = inputAddresses.stream().filter(a -> a.getStreet().contains(street)).toList();

        var pairs = StreamUtils.zip(list.stream(), result.stream(), Pair::of);

        pairs.forEach(pair -> {
            var first = pair.getFirst();
            var second = pair.getSecond();
            assertEquals(first.getId(), second.getId());
            assertEquals(first.getStreet(), second.getStreet());
            assertEquals(first.getCity(), second.getCity());
            assertEquals(first.getState(), second.getState());
            assertEquals(first.getZipCode(), second.getZipCode());
        });


    }



}
