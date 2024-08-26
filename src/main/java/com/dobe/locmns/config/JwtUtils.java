
package com.dobe.locmns.config;

import com.dobe.locmns.models.Role;
import com.dobe.locmns.models.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.token-validity}")
    private long TOKEN_VALIDITY;

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            if (claims.get("email") != null) {
                return claims.get("email", String.class);
            }
            if (claims.getSubject() != null) {
                return claims.getSubject();
            }
            if (claims.get("roles") != null) {
                return claims.get("roles", String.class);
            }
            logger.warn("Aucun identifiant utilisateur trouvé dans le token");
            return null;
        } catch (Exception e) {
            logger.error("Erreur lors de l'extraction de l'username du token", e);
            return null;
        }
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Erreur lors de l'extraction des claims du token", e);
            return null;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Utilisateur utilisateur = (Utilisateur) userDetails;
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("email", utilisateur.getEmail());
        claims.put("roles", utilisateur.getRole().stream().map(Role::getRoleName).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(utilisateur.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }



    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }
}
