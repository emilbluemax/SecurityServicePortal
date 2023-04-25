package com.example.eventManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.example.eventManagement.model.Event;


//@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	@Query("SELECT e FROM Event e WHERE e.userId = ?1")
	public List<Event> getEventById(long id);

}

