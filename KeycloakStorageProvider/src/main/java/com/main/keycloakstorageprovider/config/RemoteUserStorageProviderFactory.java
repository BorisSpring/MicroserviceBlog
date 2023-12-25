package com.main.keycloakstorageprovider.config;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class RemoteUserStorageProviderFactory implements UserStorageProviderFactory<RemoteUserStorageProvider> {
    public  static final String PROVIDER_NAME = "mysql-remote-provider";
    @Override
    public RemoteUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        return new RemoteUserStorageProvider(keycloakSession, componentModel, new UserLegacyService(keycloakSession));
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }


}
