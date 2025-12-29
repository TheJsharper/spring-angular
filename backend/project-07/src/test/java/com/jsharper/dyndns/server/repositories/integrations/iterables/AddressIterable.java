package com.jsharper.dyndns.server.repositories.integrations.iterables;

import com.jsharper.dyndns.server.entities.Address;
import com.jsharper.dyndns.server.repositories.AddressRepository;
import org.springframework.data.domain.Page;

import java.util.Iterator;

public class AddressIterable implements Iterator<Page<Address>>, Iterable<Page<Address>> {

    private Page<Address> page;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private AddressRepository ar;


    public AddressIterable(Page<Address> page, AddressRepository ar) {
        this.page = page;
        this.ar = ar;
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
    public Iterator<Page<Address>> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        this.totalPages++;
        return page.hasNext();
    }

    @Override
    public Page<Address> next() {
        var returnValue = this.page;
        this.pageSize = returnValue.getSize();
        this.totalPages = returnValue.getTotalPages();
        this.pageNumber = returnValue.getNumber();
        this.page = ar.findAllAddresses(this.page.nextOrLastPageable());
        return returnValue;
    }
}
