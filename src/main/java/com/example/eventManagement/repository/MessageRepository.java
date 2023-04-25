package com.example.eventManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventManagement.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
