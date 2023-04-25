package com.example.eventManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eventManagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email = ?1 AND u.password = ?2")
	public User validate(String email, String password);

	@Query("SELECT u.id FROM User u WHERE u.email = ?1 ")
	public long getId(String email);
}
