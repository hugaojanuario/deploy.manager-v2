package com.hugaojanuario.deploy.manager.controller;

import com.hugaojanuario.deploy.manager.domain.user.User;
import com.hugaojanuario.deploy.manager.domain.user.dtos.AuthDto;
import com.hugaojanuario.deploy.manager.domain.user.dtos.AuthResponse;
import com.hugaojanuario.deploy.manager.domain.user.dtos.CreateUserRequest;
import com.hugaojanuario.deploy.manager.infra.security.TokenService;
import com.hugaojanuario.deploy.manager.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDto authDto){
        var userNamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generatedToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid CreateUserRequest request){
        if (this.userRepository.findByEmail(request.email()) != null) return ResponseEntity.badRequest().build();

        String encryptPassword = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User(request.email(), encryptPassword, request.userType());
        newUser.setActivate(true);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
