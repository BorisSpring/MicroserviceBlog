package com.main.gatewayblog.config;

import com.main.gatewayblog.keycloak.KeycloakRoleConverter;
import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import reactor.core.publisher.Mono;
import java.util.List;


@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain serverHttpSecurity(ServerHttpSecurity serverHttpSecurity){
      return   serverHttpSecurity.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                              .pathMatchers(HttpMethod.DELETE, "/blogs/api/categories/**" ,"/slides/api/slides/*","/comments/api/comments/*","/messages/api/messages/*").hasAnyRole("ADMIN","admin")
                              .pathMatchers(HttpMethod.PUT,"/slides/api/slides/enableSlide/*","/slides/api/slides/disable/*","/slides/api/slides/*/*","/messages/api/messages/readed/*","/messages/api/messages/unread/*","/comments/api/comments/enable/*","/comments/api/comments/disable/*", "/blogs/api/categories", "/blogs/api/categories/*" , "/blogs/api/categories/*/*").hasAnyRole("ADMIN","admin")
                              .pathMatchers(HttpMethod.POST, "/blogs/api/categories","/slides/api/slides").hasAnyRole("ADMIN","admin")
                              .pathMatchers(HttpMethod.GET, "/comments/api/comments","/messages/api/messages","/slides/api/slides/allSlides").hasAnyRole("ADMIN", "admin")
                              .anyExchange().permitAll())
                    .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                            .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthorityExtractor())))
                    .csrf(ServerHttpSecurity.CsrfSpec::disable)
              .cors(corsSpec -> corsSpec.configurationSource(exchange -> {
                  CorsConfiguration corsConfiguration = new CorsConfiguration();
                  corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
                  corsConfiguration.setAllowCredentials(true);
                  corsConfiguration.setAllowedHeaders(List.of("*"));
                  corsConfiguration.addAllowedMethod("*");
                  corsConfiguration.setMaxAge(3600L);
                  corsConfiguration.setExposedHeaders(List.of("Authorization"));
                  return  corsConfiguration;
              }))
              .build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthorityExtractor(){
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
