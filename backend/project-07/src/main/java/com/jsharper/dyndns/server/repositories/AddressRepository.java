package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {
    @Query("from Address")
    Page<Address> findAllAddresses(Pageable pageable);

    @Query(value = "SELECT * FROM  ADDRESS as a ", nativeQuery = true)
    List<Address> findAllAddressesByNativeQuery();

    @Query(value="SELECT * FROM ADDRESS as a WHERE a.state=:stateName", nativeQuery = true)
    List<Address> findAllAddressesByStateName(@Param("stateName") String stateName);

    @Query(value="SELECT * FROM ADDRESS as a WHERE a.state=:city", nativeQuery = true)
    List<Address> findAllAddressesByCity(@Param("city") String city);

    @Query(value="SELECT * FROM ADDRESS as a WHERE a.street LIKE %:streetName%", nativeQuery = true)
    List<Address> findAllAddressesContainsStreetName(@Param("streetName") String streetName);
}
