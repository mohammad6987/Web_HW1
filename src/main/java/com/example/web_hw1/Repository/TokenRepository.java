package com.example.web_hw1.Repository;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.TokenPack;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface TokenRepository extends CrudRepository<TokenPack, Long> {
    TokenPack getTokenPackById(Long id);
}
