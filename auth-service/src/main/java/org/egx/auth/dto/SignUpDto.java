package org.egx.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmedPassword;
    private String role;
}
