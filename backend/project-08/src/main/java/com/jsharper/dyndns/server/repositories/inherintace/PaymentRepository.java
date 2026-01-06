package com.jsharper.dyndns.server.repositories.inherintace;

import com.jsharper.dyndns.server.entities.inheritance.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
}
