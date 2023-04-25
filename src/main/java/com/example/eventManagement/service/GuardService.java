package com.example.eventManagement.service;

import java.util.List;

import com.example.eventManagement.model.Guard;
import com.example.eventManagement.model.Message;

public interface GuardService {
	List<Guard> getAllGuards();
	void saveGuard(Guard Guard);
	Guard getGuardById(long id);
	void deleteGuardById(long id);
	
	List<Message> getAllmessages();
	void savemessage(Message message);

}
