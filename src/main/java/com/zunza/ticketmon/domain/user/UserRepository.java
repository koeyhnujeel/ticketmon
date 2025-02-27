package com.zunza.ticketmon.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query("SELECT u "
		+ "FROM User u "
		+ "WHERE u.email=:email")
	User findByEmailForOauth2(@Param("email") String email);

	boolean existsByEmail(String email);
}
