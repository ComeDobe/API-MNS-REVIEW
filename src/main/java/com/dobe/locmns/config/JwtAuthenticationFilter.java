

package com.dobe.locmns.config;

import com.dobe.locmns.repositories.UtilisateurRepository;
import com.dobe.locmns.services.Auth.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtils jwtUtils;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String requestTokenHeader = request.getHeader(AUTHORIZATION);
            String email = null;
            String token = null;

            logger.info("Processing request: {} {}", request.getMethod(), request.getRequestURI());
            logger.info("Authorization header: {}", requestTokenHeader);

            if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER)) {
                token = requestTokenHeader.substring(7);
                try {
                    email = jwtUtils.getUsernameFromToken(token);
                    logger.info("Email extracted from token: {}", email);
                } catch (IllegalArgumentException e) {
                    logger.error("Impossible d'obtenir le token JWT", e);
                } catch (ExpiredJwtException e) {
                    logger.error("Le token JWT a expiré", e);
                }
            } else {
                logger.warn("Le token JWT ne commence pas par Bearer ou est absent");
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtService.loadUserByUsername(email);
                logger.info("User details loaded: {}", userDetails);

                if (jwtUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Authentication successful for user: {}. Authorities: {}", email, userDetails.getAuthorities());
                } else {
                    logger.warn("Token validation failed for user: {}", email);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication", e);
        }

        filterChain.doFilter(request, response);
    }
}
