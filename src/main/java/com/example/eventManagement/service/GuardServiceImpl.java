package com.example.eventManagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
//import java.time.LocalDateTime;  
//import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventManagement.model.Guard;
import com.example.eventManagement.model.Message;
import com.example.eventManagement.repository.GuardRepository;
import com.example.eventManagement.repository.MessageRepository;

@Service
public class GuardServiceImpl implements GuardService {
	@Autowired
	private GuardRepository GuardRepository;

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public List<Guard> getAllGuards() {
		return GuardRepository.findAll();
	}

	@Override
	public void saveGuard(Guard Guard) {
		this.GuardRepository.save(Guard);
	}

	@Override
	public Guard getGuardById(long id) {
		Optional<Guard> optional = GuardRepository.findById(id);
		Guard Guard = null;
		if (optional.isPresent()) {
			Guard = optional.get();
		} else {
			throw new RuntimeException(" Guard not found for id :: " + id);
		}
		return Guard;
	}

	@Override
	public void deleteGuardById(long id) {
		this.GuardRepository.deleteById(id);
	}
	
	//***************************************** message *********************************************
	@Override
	public void savemessage(Message message) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		message.setDate(dtf.format(now));
		this.messageRepository.save(message);
	}
	public List<Message> getAllmessages() {
		return messageRepository.findAll();
	}
}
