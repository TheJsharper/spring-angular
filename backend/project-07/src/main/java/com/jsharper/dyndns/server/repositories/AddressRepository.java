package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {
    @Query("from Address")
    Page<Address> findAllAddresses(Pageable pageable);
}
