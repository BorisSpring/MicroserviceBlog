package com.main.keycloakstorageprovider;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KeycloakStorageProviderApplication {

	public static void main(String[] args) {

		SpringApplication.run(KeycloakStorageProviderApplication.class, args);

		
	}

}
