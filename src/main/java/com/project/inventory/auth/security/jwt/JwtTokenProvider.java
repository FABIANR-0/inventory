package com.project.inventory.auth.security.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${settings.auth.token-time}")
    private Long timeToken;

    SecretKey key = generateSecretKey();

    public String generateToken(String userName) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userName);

        //hora actual en milisegundos
        Date issuedAt = new Date(System.currentTimeMillis());

        //calcular timeToken a partir de hora actual
        Date expirationDate = new Date(issuedAt.getTime() + (timeToken * 60 * 1000));

        return Jwts
                .builder()
                .header()
                .type("JWT")
                .and()
                .claims(claims)
                .subject(userName)
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    //validar el jwt recibido en la petición

    public boolean validJwt(String jwt){
        try {
            Claims claims = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            return claims != null;
        } catch (Exception e) {
            log.error("Failed to validate token: {}", e.getMessage());
        }
        return false;
    }


    public String extractorUserName(String jwt) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

//    private Claims extractAllClaims(String jwt) {
//        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
//    }

    private static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
            return keyGenerator.generateKey();
        } catch (Exception ex) {
            log.error("Error al generar la llave secreta del JWT [{}]", ex.getMessage());
            return null;
        }
    }
}
