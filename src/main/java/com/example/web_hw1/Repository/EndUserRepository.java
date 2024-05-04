package com.example.web_hw1.Repository;

import com.example.web_hw1.Model.EndUser;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


//@Repository
/*public interface EndUserRepository extends CrudRepository<EndUser , Long>{
    Optional<EndUser> getEndUserById();
}*/
@Repository
public interface EndUserRepository {
    Optional<EndUser> getEndUserById();
}