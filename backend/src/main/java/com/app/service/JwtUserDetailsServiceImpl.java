package com.app.service;

import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.security.auth.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException(String.format("No user " +
                "found with username '%s'.", username));
        try {
            user = userRepository.findByName(username);
            if(user == null) {
                throw usernameNotFoundException;
            }
        } catch(Exception ex) {
            throw usernameNotFoundException;
        }

        return JwtUserFactory.create(user);
    }
}
