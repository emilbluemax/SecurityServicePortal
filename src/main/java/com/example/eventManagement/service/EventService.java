package com.example.eventManagement.service;

import java.util.List;

import com.example.eventManagement.model.Event;
import com.example.eventManagement.model.Feedback;

public interface EventService {
	List<Event> getAllEvents();
	void saveEvent(Event event);
	Event getEventById(long id);
	void deleteEventById(long id);

	List<Feedback> getAllFeedback();
	void saveFeedback(Feedback feedback);
}
