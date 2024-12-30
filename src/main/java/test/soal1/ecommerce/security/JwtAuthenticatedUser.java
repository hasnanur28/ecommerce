package test.soal1.ecommerce.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

public class JwtAuthenticatedUser extends UsernamePasswordAuthenticationToken {

    public JwtAuthenticatedUser(String email) {
        super(email, null, Collections.emptyList());
    }

    @Override
    public String getPrincipal() {
        return super.getPrincipal().toString();
    }
}
