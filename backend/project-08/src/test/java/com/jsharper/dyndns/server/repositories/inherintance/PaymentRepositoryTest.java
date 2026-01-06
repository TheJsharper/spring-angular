package com.jsharper.dyndns.server.repositories.inherintance;

import com.jsharper.dyndns.server.entities.inheritance.payments.Check;
import com.jsharper.dyndns.server.entities.inheritance.payments.CreditCard;
import com.jsharper.dyndns.server.repositories.inherintace.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PaymentRepositoryTest {
    @Autowired
    private PaymentRepository pr;

    @Test
    @Order(1)
    void testCreditCardEntity_whenProvidedCreditCardInstance_returnEntityCreditCard() {
        var creditCard = new CreditCard("1222520", 350.85);

        var storedPayment = pr.save(creditCard);

        System.out.println(creditCard);
        System.out.println(storedPayment);

        Assertions.assertEquals(creditCard, storedPayment);
    }


    @Test
    @Order(2)
    void testCheckEntity_whenProvidedCheckInstance_returnEntityCheck() {
        var check = new Check("8988558s", 250.98);

        var storedPayment = pr.save(check);

        System.out.println(check);
        System.out.println(storedPayment);

        Assertions.assertEquals(check, storedPayment);
    }
}
