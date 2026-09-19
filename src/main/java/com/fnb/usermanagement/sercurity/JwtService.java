package com.fnb.usermanagement.sercurity;

import com.fnb.usermanagement.entity.User;

public interface JwtService {

    String generateToken(User user);
    boolean validateToken(String token, String email);
    String extractEmailFromToken(String token);
}
