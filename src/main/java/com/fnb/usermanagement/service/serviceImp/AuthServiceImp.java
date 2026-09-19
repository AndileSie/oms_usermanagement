package com.fnb.usermanagement.service.serviceImp;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fnb.usermanagement.dto.LoginRequest;
import com.fnb.usermanagement.dto.LoginResponse;
import com.fnb.usermanagement.dto.RegisterRequest;
import com.fnb.usermanagement.dto.UserResponse;
import com.fnb.usermanagement.entity.Role;
import com.fnb.usermanagement.entity.User;
import com.fnb.usermanagement.entity.UserCredentials;
import com.fnb.usermanagement.repository.UserCredentialsRepository;
import com.fnb.usermanagement.repository.UserRepository;
import com.fnb.usermanagement.sercurity.JwtService;
import com.fnb.usermanagement.service.AuthService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class AuthServiceImp implements AuthService {

    private  final UserRepository userRepository;
    private  final UserCredentialsRepository userCredentialsRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    @Transactional 
    public UserResponse regitserUser(RegisterRequest request) {
        User user = User.builder()
            .email(request.getEmail())
            .firstName(request.getFirstName())
            .surname(request.getSurname())
            .role(Role.CUSTOMER)
            .build();
        
        user = userRepository.save(user);
        UserCredentials userCredentials = UserCredentials.builder()
            .user(user)
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
        userCredentialsRepository.save(userCredentials);
        return toUserResponse(user);
    }
    @Override
    public LoginResponse login(LoginRequest login) {
        User user = userRepository.findByEmail(login.getEmail());
        if (user == null) {
            return  null;
        }
        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
            .customerId(user.getCustomerId())
            .role(user.getRole().name())
            .token(token)
            .email(user.getEmail())
            .build();

    }

    private UserResponse toUserResponse(User user){
        return UserResponse.builder()
            .customerId(user.getCustomerId())
            .firstName(user.getFirstName())
            .surname(user.getSurname())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
    }

    

}
