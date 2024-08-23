package com.dobe.locmns.config;

import com.dobe.locmns.repositories.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtils jwtUtils;
    private final UtilisateurRepository utilisateurRepository;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final int TOKEN_PREFIX_LENGTH = 7;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.debug("JwtAuthenticationFilter is being executed");
        String authHeader = request.getHeader(AUTHORIZATION);
        logger.debug("Auth header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            logger.debug("No valid auth header found");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(TOKEN_PREFIX_LENGTH);
        String utilisateurEmail = jwtUtils.extractUsername(jwt);
        logger.debug("Extracted email from JWT: {}", utilisateurEmail);

        if (utilisateurEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = utilisateurRepository.findByEmail(utilisateurEmail)
                        .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé ou non validé par son jwt"));

                logger.debug("User details found for email: {}", utilisateurEmail);

                if (jwtUtils.isTokenValid(jwt, userDetails)) {
                    List<GrantedAuthority> authorities = jwtUtils.getAuthorities(jwt);
                    logger.debug("Authorities extracted from JWT: {}", authorities);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    logger.debug("Authentication set in SecurityContext for user: {}", utilisateurEmail);
                } else {
                    logger.warn("Invalid token for user: {}", utilisateurEmail);
                }
            } catch (EntityNotFoundException e) {
                logger.error("User not found for email: {}", utilisateurEmail, e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
