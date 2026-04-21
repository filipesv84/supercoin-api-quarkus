package org.acme.auth;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class TokenUtils {

    public String gerarToken(String email, String role) {
        return Jwt.issuer("https://acme.org")
                .upn(email)
                .groups(Set.of(role))
                .expiresIn(3600)
                .sign();
    }
}
