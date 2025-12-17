package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductPageableAndSortableRepository extends PagingAndSortingRepository<ProductEntity, Long>, CrudRepository<ProductEntity, Long> {
}
