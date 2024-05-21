package com.example.web_hw1.Service;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Repository.EndUserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;

public class EndUserDetailsServiceTest {
    @Autowired
    EndUserDetailsService endUserDetailsService;
    @Test
    void passwordHashTest() throws NoSuchAlgorithmException {
        endUserDetailsService = new EndUserDetailsService(null , null , null);
        String in = "qwerty";
        Assertions.assertEquals("65E84BE33532FB784C48129675F9EFF3A682B27168C0EA744B2CF58EE02337C5" ,endUserDetailsService.hashString(in) );
        in  = "123456789";
        Assertions.assertEquals(endUserDetailsService.hashString(in) , "15E2B0D3C33891EBB0F1EF609EC419420C20E320CE94C65FBC8C3312448EB225");
    }
    @Test
    void userNotNull() throws Exception {
        String out = endUserDetailsService.createUser(new EndUserDto());
        Assertions.assertNotNull(out);
    }

}
