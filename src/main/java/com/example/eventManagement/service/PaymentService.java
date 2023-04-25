package com.example.eventManagement.service;

import java.util.List;

import com.example.eventManagement.model.Payment;

public interface PaymentService {

	public void savepayment(Payment payment) ;
	public List<Payment> getAllpayments();
}
