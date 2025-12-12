package com.example.demo.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.validateAndGetClaims(token);
                String subj = claims.getSubject();
                if (subj != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Long userId = Long.parseLong(subj);
                    Usuario usuario = usuarioRepository.findById(userId).orElse(null);
                    if (usuario != null) {
                        String roleName = (String) claims.get("rol");
                        String springRole = "ROLE_" + roleName;

                        System.out.println("DEBUG JWT - Rol final: " + springRole);

                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(springRole);

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                usuario, null, Collections.singletonList(authority));
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error en JWT Authentication Filter: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
