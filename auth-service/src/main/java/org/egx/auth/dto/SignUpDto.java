package org.egx.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDto {
    @NotNull
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String confirmedPassword;
    @NotNull
    private String role;
}
