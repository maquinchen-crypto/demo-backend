package com.example.demo.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Obtención de token JWT")
public class AuthController {
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private JwtEncoder jwtEncoder;
@Value("${jwt.expiration-minutes}")
private long expirationMinutes;

public record LoginRequest(String username, String password) {}

@PostMapping("/login")
@Operation(summary = "Obtener un token JWT con usuario y contraseña")
public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
    Authentication auth;
    try {
        auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.username(), request.password()));
    } catch (AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales inválidas"));
    }
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
    .issuer("demo")
    .issuedAt(now)
    .expiresAt(now.plus(expirationMinutes, ChronoUnit.MINUTES))
    .subject(auth.getName())
    .build();
    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
    String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    return ResponseEntity.ok(Map.of(
    "token", token,
    "tokenType", "Bearer",
    "expiresIn", expirationMinutes * 60));
}
}
