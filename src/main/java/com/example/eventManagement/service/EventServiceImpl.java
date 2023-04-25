package com.example.eventManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventManagement.model.Event;
import com.example.eventManagement.model.Feedback;
import com.example.eventManagement.repository.EventRepository;
import com.example.eventManagement.repository.FeedbackRepository;

@Service

public class EventServiceImpl implements EventService{

	@Autowired
	private FeedbackRepository feedbackRepository;
	@Autowired
	private EventRepository eventRepository;


	@Override
	public List<Event> getAllEvents(){
		return eventRepository.findAll();
	}

	@Override
	public void saveEvent(Event event) {
		this.eventRepository.save(event);
	}

	@Override
    public Event getEventById(long id) {
        Optional<Event> optional = eventRepository.findById(id);
        Event event = null;
        if(optional.isPresent()) {
            event = optional.get();
        }
        else {
            throw new RuntimeException("Event not found for ID :: "+id);
        }
        return event;
    }

	@Override
    public void deleteEventById(long id) {
        this.eventRepository.deleteById(id);
    }



	@Override
	public List<Feedback> getAllFeedback(){
		return feedbackRepository.findAll();
	}

	@Override
	public void saveFeedback(Feedback feedback) {
		this.feedbackRepository.save(feedback);
	}
}
