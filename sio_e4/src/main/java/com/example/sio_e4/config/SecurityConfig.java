package com.example.sio_e4.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

@Configuration
public class SecurityConfig {

  @Bean
  @Order(1)
  public SecurityFilterChain asFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(Customizer.withDefaults());

    http.exceptionHandling(c -> c.authenticationEntryPoint(
        new LoginUrlAuthenticationEntryPoint("/login")
    ));

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain appFilterChain(HttpSecurity http) throws Exception {
    http.formLogin();

    http.authorizeHttpRequests().anyRequest().authenticated();

    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient c1 = RegisteredClient
        .withId("1")
        .clientId("client")
        .clientSecret("secret")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .scope(OidcScopes.OPENID)
        .redirectUri("http://springio.net/auth")
        .tokenSettings(TokenSettings.builder()
            .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
            .accessTokenTimeToLive(Duration.ofHours(24))
            .refreshTokenTimeToLive(Duration.ofHours(24))
            .build())
        .clientSettings(ClientSettings.builder()
//            .requireProofKey(false)
            .build())
        .build();

    return new InMemoryRegisteredClientRepository(c1);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails u1 = User.withUsername("john")
        .password("12345")
        .authorities("read")
        .build();

    return new InMemoryUserDetailsManager(u1);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        .build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    KeyPair keypair = generator.generateKeyPair();

    RSAPublicKey rsaPublicKey = (RSAPublicKey) keypair.getPublic();
    RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keypair.getPrivate();

    RSAKey key = new RSAKey.Builder(rsaPublicKey)
        .privateKey(rsaPrivateKey)
        .keyID("abcde")
        .build();

    JWKSet set = new JWKSet(key);
    return new ImmutableJWKSet<>(set);
  }
}
