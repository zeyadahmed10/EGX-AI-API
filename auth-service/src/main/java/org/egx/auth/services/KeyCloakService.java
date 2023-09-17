package org.egx.auth.services;

import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.egx.auth.dto.SignUpDto;
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
    public String createUser(SignUpDto signUpDto){
        var passwordCredentials =createPasswordCredentials(signUpDto.getPassword());
        var roleRepresentation = getRoleRepresentation(signUpDto.getRole());
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(signUpDto.getUsername());
        userRepresentation.setEmail(signUpDto.getEmail());
        userRepresentation.setCredentials(Arrays.asList(passwordCredentials));
        userRepresentation.setEnabled(true);
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = keycloak.realm(realm).users().get(userId);
        userResource.roles().realmLevel().add(Arrays.asList(roleRepresentation));
        log.info("User created successfully with Id: "+userId);
        return "User created successfully with Id: "+userId;
    }
    public CredentialRepresentation createPasswordCredentials(String password) {
        // TODO check password validity
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
    public RoleRepresentation getRoleRepresentation(String roleName){
        return keycloak.realm(realm).roles().get(roleName).toRepresentation();
    }

}

