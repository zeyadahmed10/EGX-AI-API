package org.egx.auth.services;

import exceptions.ResourceExistedException;
import exceptions.ResourceNotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.egx.auth.dto.LoginDto;
import org.egx.auth.dto.SignUpDto;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@Slf4j
public class KeyCloakService {

    @Autowired
    private Keycloak keycloak;
    @Autowired
    RestTemplate restTemplate;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.loginUrl}")
    private String loginUrl;

    public ResponseEntity<Object> getUserToken(LoginDto signinDto){
        //creating headers for request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString()); //Optional in case server sends back JSON data
        //creating body for request
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", OAuth2Constants.PASSWORD);
        requestBody.add("username", signinDto.getUsername());
        requestBody.add("password", signinDto.getPassword());
        requestBody.add("client_id", clientId);
        HttpEntity<MultiValueMap<String, String>> loginEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(loginUrl, HttpMethod.POST, loginEntity, Object.class);

    }
    public String createUser(SignUpDto signUpDto) throws IllegalAccessException {
        var passwordCredentials =createPasswordCredentials(signUpDto);
        var roleRepresentation = getRoleRepresentation(signUpDto.getRole());
        var userRepresentation = getUserRepresentation(signUpDto);
        userRepresentation.setCredentials(Arrays.asList(passwordCredentials));
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if(!(response.getStatus()>=200&&response.getStatus()<300)){
            log.error("error creating user "+ signUpDto);
            throw new RuntimeException("error creating user: "
                    + signUpDto +" with code: "+response.getStatus());
        }
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = keycloak.realm(realm).users().get(userId);
        userResource.roles().realmLevel().add(Arrays.asList(roleRepresentation));
        log.info("User created successfully with Id: "+userId);
        return "User created successfully with Id: "+userId;
    }
    public CredentialRepresentation createPasswordCredentials(SignUpDto signUpDto) throws IllegalAccessException {
        if(!signUpDto.getPassword().equals(signUpDto.getConfirmedPassword())){
            log.error("Confirmed password does not match password");
            throw new IllegalAccessException("Confirmed password does not match password");
        }
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(signUpDto.getPassword());
        return passwordCredentials;
    }
    public RoleRepresentation getRoleRepresentation(String roleName){
        RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(roleName).toRepresentation();
        if(roleRepresentation==null){
            log.error("Role " + roleName + "Not found");
            throw new ResourceNotFoundException("Role " + roleName + " not found");
        }
        return roleRepresentation;
    }
    public UserRepresentation getUserRepresentation(SignUpDto signUpDto){
        boolean userExists = keycloak.realm(realm).users().searchByUsername(signUpDto.getUsername() , true).size()>0;
        if(userExists){
            log.error("User already exists with username " + signUpDto.getUsername());
            throw new ResourceExistedException("User already exists with username " + signUpDto.getUsername());
        }
        boolean emailExists = keycloak.realm(realm).users().searchByEmail(signUpDto.getEmail() , true).size()>0;
        if(emailExists){
            log.error("User already exists with emial " + signUpDto.getEmail());
            throw new ResourceExistedException("User already exists with username " + signUpDto.getEmail());
        }
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(signUpDto.getUsername());
        userRepresentation.setEmail(signUpDto.getEmail());
        userRepresentation.setFirstName(signUpDto.getFirstName());
        userRepresentation.setLastName(signUpDto.getLastName());
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

}

