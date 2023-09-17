package org.egx.auth.services;

import jakarta.ws.rs.core.Response;
import org.egx.auth.dto.SignUpDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class KeyCloakService {

    @Autowired
    private Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    public void createUser(SignUpDto signUpDto){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setRealmRoles(Arrays.asList(signUpDto.getRole()));
        userRepresentation.setUsername(signUpDto.getUsername());
        userRepresentation.setEmail(signUpDto.getEmail());
        var passwordCredentials =createPasswordCredentials(signUpDto.getPassword());
        userRepresentation.setCredentials(Arrays.asList(passwordCredentials));
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        System.out.println(response.toString());
    }
    public static CredentialRepresentation createPasswordCredentials(String password) {
        // TODO check password validity
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}

