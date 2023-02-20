package com.xcale.challenge.group.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xcale.challenge.group.model.Group;

@Repository
public interface GroupRepository  extends CrudRepository<Group,Integer>{
	
	Optional<Group> findByName(String name);

}
