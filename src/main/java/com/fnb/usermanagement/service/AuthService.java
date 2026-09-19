package com.fnb.usermanagement.service;

import com.fnb.usermanagement.dto.RegisterRequest;
import com.fnb.usermanagement.dto.*;

public interface AuthService {

    UserResponse regitserUser(RegisterRequest request);
    LoginResponse login(LoginRequest login);

}
