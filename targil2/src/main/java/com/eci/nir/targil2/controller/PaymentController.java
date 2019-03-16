package com.eci.nir.targil2.controller;

import com.eci.nir.targil2.model.Payment;
import com.eci.nir.targil2.model.PaymentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class PaymentController {


    @RequestMapping("/payments")
    public List<Payment> getPaymentToProcess() {
        List<Payment> result = new ArrayList<Payment>();
        Payment x = new Payment();
        x.setAmount("87");
        x.setDate(new Date());
        x.setPaymentType(PaymentType.CREDIT);
        x.setTransactionId("24323423");
        result.add(x);
        return result;
    }
}
