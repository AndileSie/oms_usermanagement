package com.fnb.usermanagement.service.serviceImp;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fnb.usermanagement.entity.User;
import com.fnb.usermanagement.entity.UserCredentials;
import com.fnb.usermanagement.repository.UserCredentialsRepository;
import com.fnb.usermanagement.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor 
public class UserDetailsImp implements UserDetailsService{


    private  final UserRepository userRepository;
    private  final UserCredentialsRepository userCredentialsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        UserCredentials userCredentials = userCredentialsRepository.findByUser_CustomerId(user.getCustomerId());

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(userCredentials.getPassword())
            .authorities(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            .build();
    }

}
