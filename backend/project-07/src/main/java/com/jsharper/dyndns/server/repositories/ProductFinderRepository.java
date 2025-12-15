package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductFinderRepository extends CrudRepository<ProductEntity, Long> {
    List<ProductEntity> findByName(String name);

    List<ProductEntity> findByNameAndDesc(String name, String desc);

    List<ProductEntity> findByPriceGreaterThan(double price);

    List<ProductEntity> findByDescContains(String desc);
}
