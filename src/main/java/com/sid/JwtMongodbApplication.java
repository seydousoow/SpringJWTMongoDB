package com.sid;

import com.sid.dao.UserRepository;
import com.sid.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtMongodbApplication.class, args);
    }

    /*
     * Bean for BCrypt encoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder();}

}
