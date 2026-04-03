package com.hugaojanuario.deploy.manager.service;

import com.hugaojanuario.deploy.manager.domain.user.User;
import com.hugaojanuario.deploy.manager.domain.user.dtos.AuthRegisterResponse;
import com.hugaojanuario.deploy.manager.domain.user.dtos.CreateUserRequest;
import com.hugaojanuario.deploy.manager.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public AuthRegisterResponse register (@Valid CreateUserRequest request){
        if (this.userRepository.findByEmail(request.email()) != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");

        String encryptPassword = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User(request.email(), encryptPassword, request.userType());
        newUser.setActivate(true);
        this.userRepository.save(newUser);

        return new AuthRegisterResponse(newUser);
    }
}
