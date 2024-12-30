package test.soal1.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Gunakan `SecretKey` dari API terbaru
    private final SecretKey secretKey = Keys.hmacShaKeyFor("my-secret-key-my-secret-key-my-secret-key".getBytes());

    private final long expirationMs = 86400000; // 1 hari

    // Generate JWT Token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256) // Gunakan secretKey dan algoritma
                .compact();
    }

    // Extract email (subject) dari JWT Token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Validasi JWT Token
    public boolean validateToken(String token) {
        try {
            getClaims(token); // Memastikan token valid dengan mem-parsing
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Ambil semua Claims dari token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
