package com.example.web_hw1.Repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface TokenRepository extends CrudRepository<Collection<String> , String> {
    Collection<String> getCollectionByusername(String username);
}
