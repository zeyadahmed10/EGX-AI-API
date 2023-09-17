package org.egx.auth.services;

import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.egx.auth.dto.SignUpDto;
import org.egx.auth.exception.ResourceExistedException;
import org.egx.auth.exception.ResourceNotFoundException;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class KeyCloakService {

    @Autowired
    private Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    public String createUser(SignUpDto signUpDto) throws IllegalAccessException {
        var passwordCredentials =createPasswordCredentials(signUpDto);
        var roleRepresentation = getRoleRepresentation(signUpDto.getRole());
        var userRepresentation = getUserRepresentation(signUpDto);
        userRepresentation.setCredentials(Arrays.asList(passwordCredentials));
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if(response.getStatus()!=201){
            log.error("error creating user "+signUpDto.toString());
            throw new RuntimeException("Error with status code: "+response.getStatus());
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

