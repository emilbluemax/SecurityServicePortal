package com.example.eventManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.eventManagement.model.Guard;


@Repository
public interface GuardRepository extends JpaRepository<Guard, Long>{
	@Query("SELECT g FROM Guard g WHERE g.email = ?1 AND g.password = ?2")
	public Guard validate(String email, String password);
	
	@Query("SELECT g.id FROM Guard g WHERE g.email = ?1 ")
	public long getId(String email);
	
	@Query("SELECT g FROM Guard g WHERE g.id = ?1")
	public List<Guard> getGuardById(long id);
}
