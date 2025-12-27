package com.jsharper.dyndns.server.repositories.integrations.iterables;

import com.jsharper.dyndns.server.entities.ProductEntity;
import com.jsharper.dyndns.server.repositories.ProductPageableAndSortableRepository;
import org.springframework.data.domain.Page;

import java.util.Iterator;

public class ProductIterable implements Iterator<Page<ProductEntity>>, Iterable<Page<ProductEntity>> {
    private Page<ProductEntity> page;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private ProductPageableAndSortableRepository er;

    public ProductIterable(Page<ProductEntity> page, ProductPageableAndSortableRepository er) {
        this.page = page;
        this.er = er;
        this.totalPages = page.getTotalPages();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public boolean hasNext() {
        this.totalPages++;
        return page.hasNext();
    }

    @Override
    public Page<ProductEntity> next() {
        var returnValue = this.page;
        this.pageSize = returnValue.getSize();
        this.totalPages = returnValue.getTotalPages();
        this.pageNumber = returnValue.getNumber();
        this.page = er.findAll(this.page.nextOrLastPageable());
        return returnValue;
    }

    @Override
    public Iterator<Page<ProductEntity>> iterator() {
        return this;
    }
}