package com.example.web_hw1.Repository;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.TokenPack;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface TokenRepository extends CrudRepository<TokenPack, String> {
    TokenPack getTokenPackByName(String name);
    Collection<TokenPack> findByOwnerUsername(String username);
    void deleteByName(String name);
    void deleteByTokenValue(String tokenValue);
}
