package com.xcale.challenge.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xcale.challenge.user.model.User;

@Repository
public interface UserRepository extends  CrudRepository<User,Integer>{

	Optional<User> findByNumber(Long number);
}
