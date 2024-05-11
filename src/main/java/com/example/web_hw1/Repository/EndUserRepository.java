package com.example.web_hw1.Repository;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


@Repository
public interface EndUserRepository extends CrudRepository<EndUser , Long>{
    Optional<EndUser> getEndUserById(Long id);
    Optional<EndUser> getEndUserByUsername(String username);

}
