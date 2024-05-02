package com.example.web_hw1.Service;

import com.example.web_hw1.Repository.EndUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndUserService {
    @Autowired
    EndUserRepository endUserRepository;
}
