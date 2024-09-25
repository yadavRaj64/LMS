package com.lms.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lms.server.model.User;
import com.lms.server.model.UserPrincipal;
import com.lms.server.repository.UserRepository;

@Service
public class UserPrincipalService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUserID(Long userID) throws UsernameNotFoundException {
        User user = userRepository.findByUserID(userID);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrincipal(user);
    }
}