package com.QLGym.GymError.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.io.Decoders;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Value("${jwt.secret:yourSecretKeyForJWTGenerationAndValidationThatIsLongEnough}")
    private String secret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/login", "/api/register").permitAll()

                // Endpoints requiring specific roles (Authorities)
                // Note: In OAuth2 Resource Server, roles are often mapped to Scopes/Authorities.
                // Ensure your JWT generation includes a 'scope' or 'authorities' claim
                // with values like 'ROLE_CHUPHONG', 'ROLE_ADMIN', 'ROLE_PT', etc.
                // Or, if the backend uses the 'role' claim directly as authority, use that name.
                // Based on the previous UserDetailsService, we used ROLE_ROLENAME format.
                .requestMatchers("/api/nhan-vien-le-tan/**").hasAnyAuthority("ROLE_CHUPHONG", "ROLE_ADMIN")
                // Add role-based rules for other controllers here similarly
                // Example: .requestMatchers("/api/pt/**").hasAnyAuthority("ROLE_CHUPHONG", "ROLE_ADMIN", "ROLE_PT")


                // All other /api endpoints require authentication (if not matched above)
                .requestMatchers("/api/**").authenticated()

                // Permit all other requests (e.g., static files like HTML, CSS, JS)
                .anyRequest().permitAll()
            );

        // Configure OAuth2 Resource Server for JWT validation
        http.oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
            )
        );


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        // If frontend is served from a different path and needs CORS for non-api resources,
        // consider adding a broader rule if necessary, e.g., source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // Configure JWT Decoder for OAuth2 Resource Server
    @Bean
    public JwtDecoder jwtDecoder() {
        // Ensure the secret key is sufficient for HS256 (at least 32 bytes or 256 bits)
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (IllegalArgumentException e) {
            // If not base64, treat as plain string bytes
            keyBytes = secret.getBytes();
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    // Configure JwtAuthenticationConverter to extract authorities from the 'role' claim
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extract the 'role' claim, which is a String
            String role = jwt.getClaimAsString("role");
            if (role == null) {
                return Collections.emptyList();
            }
            // Convert the role string (e.g., "ROLE_CHUPHONG") into a collection of GrantedAuthority
            return Collections.singletonList(new SimpleGrantedAuthority(role));
            // If you had multiple roles in a claim (e.g., as a List<String>), you would process the list here
            // Example if 'roles' claim was List<String>: jwt.getClaimAsStringList("roles").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        });
        return converter;
    }
} 