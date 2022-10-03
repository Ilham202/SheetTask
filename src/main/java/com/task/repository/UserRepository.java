package com.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.task.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);
	
	@Query(value = "select ID from USERS where username=?1", nativeQuery = true)
	int getUserId(String uname);
}
