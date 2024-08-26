package com.dobe.locmns.config;

import com.dobe.locmns.repositories.UtilisateurRepository;
import com.dobe.locmns.services.Auth.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    private final JwtUtils jwtUtils;
    private final UtilisateurRepository utilisateurRepository;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private  final JwtService jwtService;
    private static final int TOKEN_PREFIX_LENGTH = 7;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader(AUTHORIZATION);
//        String jwt;
//
//        if (authHeader == null || !authHeader.startsWith(BEARER)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        jwt = authHeader.substring(TOKEN_PREFIX_LENGTH);
//        String utilisateurEmail = jwtUtils.extractUsername(jwt);
//
//        if (utilisateurEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = utilisateurRepository.findByEmail(utilisateurEmail)
//                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé ou non validé par son jwt"));
//
//            if (jwtUtils.isTokenValid(jwt, userDetails)) {
//                List<GrantedAuthority> authorities = jwtUtils.getAuthorities(jwt);
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        String token = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);
            try {
                email = jwtUtils.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("implossible d'obtenir le token JWT");
            } catch (ExpiredJwtException e) {
                System.out.println("le token JWT a expiré");
            }
        } else {
            System.out.println(" Bearer ");
        }
        if (email  != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(email);
            if (jwtUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}




