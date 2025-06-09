package community.auth.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    private final SecretKey key;
    private static final long TOKEN_EXPIRATION_TIME = 1000L * 60 * 60; // 1 hour

    public TokenProvider(@Value("${secret-key}") String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String createToken(Long userId, String role) {
        Claims claims = Jwts.claims()
                .subject(userId.toString())
                .build();

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expirationDate)
                .claim("role", role)
                .signWith(key)
                .compact();
    }

    public Long getUserId(String token) {
        return Long.parseLong(
                Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject()
        );
    }
    public String getUserRole(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("JWT 검증 실패: " + e.getMessage()); // Custom Exception ㅊㅜㄱㅏ
            return false;
        }
    }
}
