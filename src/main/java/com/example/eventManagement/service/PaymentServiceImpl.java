package com.example.eventManagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventManagement.model.Payment;
import com.example.eventManagement.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService{
	@Autowired
	private PaymentRepository PaymentRepository;
	
	public void savepayment(Payment payment) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		payment.setDate(dtf.format(now));
		this.PaymentRepository.save(payment);
	}
	public List<Payment> getAllpayments() {
		return PaymentRepository.findAll();

	}
}
