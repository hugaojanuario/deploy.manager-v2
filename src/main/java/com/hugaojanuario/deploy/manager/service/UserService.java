package com.hugaojanuario.deploy.manager.service;

import com.hugaojanuario.deploy.manager.domain.user.User;
import com.hugaojanuario.deploy.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void softDeleteUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        user.setActivate(false);

        userRepository.save(user);
    }

}
