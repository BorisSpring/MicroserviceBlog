package com.main.keycloakstorageprovider.config;

import com.main.keycloakstorageprovider.model.UserDto;
import com.main.keycloakstorageprovider.model.VerifyPasswordResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

import java.io.UnsupportedEncodingException;


@Getter
@Setter
@Slf4j
public class RemoteUserStorageProvider implements UserStorageProvider, CredentialInputValidator, UserLookupProvider {

    private   KeycloakSession keycloakSession;
    private  ComponentModel componentModel;
    private UserLegacyService userLegacyService;

//    @Value("${authServiceUrl}")
//    private  String authServiceUrl;


    public RemoteUserStorageProvider(KeycloakSession keycloakSession, ComponentModel componentModel, UserLegacyService userLegacyService) {
        this.keycloakSession = keycloakSession;
        this.componentModel = componentModel;
        this.userLegacyService = userLegacyService;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realmModel, UserModel userModel, String credentialType) {
        return userModel.credentialManager().isConfiguredFor(credentialType);
    }


    @Override
    public boolean isValid(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {
        VerifyPasswordResponse verifyPasswordResponse = null;
        try {
            verifyPasswordResponse = userLegacyService.verifyUserPassword(userModel.getUsername(), credentialInput.getChallengeResponse());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return verifyPasswordResponse != null && verifyPasswordResponse.isValid();
    }

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(RealmModel realmModel, String s) {
        return null;
    }

    @Override
    public UserModel getUserByUsername(RealmModel realmModel, String username) {
//        UserDto user = fetchUser(username);
        UserDto user = userLegacyService.getUserByUserName(username);
        if(user == null)
            return null;

        return  createUserModel(username, realmModel);
    }


    @Override
    public UserModel getUserByEmail(RealmModel realmModel, String s) {
        return null;
    }

    private UserModel createUserModel(String username, RealmModel realmModel) {
        return new AbstractUserAdapter(keycloakSession,realmModel, componentModel) {
            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public SubjectCredentialManager credentialManager() {
                return null;
            }
        };
    }

//    private UserDto fetchUser(String username){
//        System.out.println("Fetchign user with username " + username);
//        try {
//            return SimpleHttp.doGet(authServiceUrl + "/users/" + username, keycloakSession).asJson(UserDto.class);
//        }catch (IOException e){
//
//            System.out.println(e.getMessage());
//            log.error(e.getMessage());
//            log.error("Error fetching user details!");
//        }
//        return null;
//    }
//
//    private VerifyPasswordResponse verifyPasswordResponse(String username, String password){
//        try {
//            return SimpleHttp.doPost(authServiceUrl + "/users/" + username + "/verify-password", keycloakSession)
//                                                                .json(new VerifyPasswordRequest(password))
//                                                                .asJson(VerifyPasswordResponse.class);
//        }catch (Exception e){
//            log.warn("There was problem sending http request for verifying password!");
//        }
//        return  new VerifyPasswordResponse(false);
//    }
}
