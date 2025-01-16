package com.example.SynClock.services;

import com.example.SynClock.model.User;
import com.example.SynClock.repositories.UserRepository;
import com.example.SynClock.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        return JwtUserDetails.create(user);
//    }
    public UserDetails loadUserByid(Long uuid){
        User user = userRepository.findById(uuid).get();
        return JwtUserDetails.create(user);
    }
}
