package com.example.eventManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.example.eventManagement.model.Feedback;


//@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
