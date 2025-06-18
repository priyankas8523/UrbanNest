package com.UrbanNest.master.service.serviceImpl;

import com.UrbanNest.master.dto.LoginRequest;
import com.UrbanNest.master.dto.LoginResponse;
import com.UrbanNest.master.dto.PersonDto;
import com.UrbanNest.master.dto.ResetPasswordRequest;
import com.UrbanNest.master.service.IamService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class IamServiceImpl implements IamService {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-url}")
    private String baseUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.post-logout-redirect-uri}")
    private String postLogoutRedirectUri;

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private RestTemplate restTemplate;

    private UsersResource usersResource = null;

    private RolesResource rolesResource = null;

    private RealmResource realmResource = null;

    @PostConstruct()
    private void init() {
        try {
            realmResource = this.keycloak.realm(realm);
            usersResource = realmResource.users();
            rolesResource = realmResource.roles();
        } catch (Exception e) {
            log.error("Error while initiating IAM Configuration", e);
        }
    }

    public String addUser(PersonDto user, ResetPasswordRequest password){
        UserRepresentation iamUser = new UserRepresentation();
        iamUser.setEmail(user.getEmail());
        iamUser.setUsername(user.getFirstName());
        iamUser.setFirstName(user.getFirstName());
        iamUser.setLastName(user.getLastName());
        iamUser.setEnabled(true);

        Response response =  usersResource.create(iamUser);
        
        System.out.println(response.getStatus() + " " + response.getStatusInfo());
        
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: " + response.getStatus());
        }
        String userId = CreatedResponseUtil.getCreatedId(response);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password.getNewPassword());

//        RealmResource realmResource = keycloak.realm("your-realm-name");
//        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        userResource.resetPassword(passwordCred);

        RoleRepresentation role = rolesResource.get(user.getRole().name()).toRepresentation();
        usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(role));
        System.out.println("IAM----------------------------------------------------------------------------------------------------------------------------");
        return userId;
    }


    @Override
    public void resetPassword(PersonDto user, String newPassword) {
        UserResource userResource = usersResource.get(user.getIamId());
        UserRepresentation userRepresentation = userResource.toRepresentation();

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(newPassword);

            userResource.resetPassword(passwordCred);
            userRepresentation.setEmailVerified(true);
            userResource.update(userRepresentation);
    }

    @Override
    public Optional<PersonDto> findByEmail(String email) {
        Optional<UserRepresentation> existingUser = Optional.empty();
        try {
            existingUser = usersResource.searchByEmail(email, true).stream().findFirst();
        } catch (Exception e) {
            System.out.println("ERROR!!!!!!!");
        }
        return existingUser.isPresent() ? existingUser.map(this::mapToPersonDto) : Optional.empty();
    }


    private PersonDto mapToPersonDto(UserRepresentation existingUser) {
        return PersonDto.builder()
                .firstName(existingUser.getFirstName())
                .lastName(existingUser.getLastName())
                .iamId(existingUser.getId())
                .email(existingUser.getEmail())
                .build();
    }
    @Override
    public LoginResponse getAccessToken(LoginRequest authRequest) {
        String url = String.format("%s/realms/%s/protocol/openid-connect/token", baseUrl, realm);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("username", authRequest.getEmail());
        formData.add("password", authRequest.getPassword());
        formData.add("scope", "openid");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(url, requestEntity, LoginResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Invalid credentials: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Login failed: ", e);
        }

        return null;
    }

    @Override
    public String logout() {
        HttpServletRequest httpServletRequest = (HttpServletRequest) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession =httpServletRequest.getSession(false);
        if(Objects.nonNull(httpSession)){
            httpSession.invalidate();
        }

        return String.format("%s/realms/%s/protocol/openid-connect/logout?redirect_uri=%s"
        ,baseUrl
        ,realm
        ,postLogoutRedirectUri
        );
    }


}
