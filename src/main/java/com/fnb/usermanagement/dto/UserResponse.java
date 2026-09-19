package com.fnb.usermanagement.dto;

import lombok.Builder;

@Builder 
public class UserResponse {
    String firstName;
    String email;
    String role;
    String surname;
    Long customerId;
}
