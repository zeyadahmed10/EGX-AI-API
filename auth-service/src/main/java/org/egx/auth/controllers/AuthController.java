package org.egx.auth.controllers;


import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.egx.auth.dto.LoginDto;
import org.egx.auth.dto.SignUpDto;
import org.egx.auth.services.KeyCloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private KeyCloakService keyCloakService;
    @PostMapping("/signup")
    public String signupUser(@Valid @RequestBody  SignUpDto signUpDto) throws IllegalAccessException {
        return keyCloakService.createUser(signUpDto);
    }
    @PostMapping("/signin")
    public ResponseEntity<Object> signinUser(@Valid @RequestBody LoginDto signInDto){
        return keyCloakService.getUserToken(signInDto);
    }


}
