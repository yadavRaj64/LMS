package com.lms.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lms.server.model.UserRole;
import com.lms.server.model.User;
import com.lms.server.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUserID((long) 0) == null) {
            User adminUser = new User();
            adminUser.setUserID((long) 0);
            adminUser.setPassword(passwordEncoder.encode("password"));
            adminUser.setRole(UserRole.ADMIN);
            userRepository.save(adminUser);
        }
    }
}
