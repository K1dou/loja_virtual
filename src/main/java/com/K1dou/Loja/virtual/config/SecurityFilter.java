package com.K1dou.Loja.virtual.config;


import com.K1dou.Loja.virtual.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {


            String token = recoverToken(request);
            if (token != null) {
                //retorna um usuario
                var login = tokenService.validateToken(token);

                //usernull
                UserDetails user = usuarioRepository.findByLogin(login);

                //PROVAVELMENTEAQ
                var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Ocorreu um erro no sistema, avise o administrador: \n" + e.getMessage());
        }
    }


    private String recoverToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) return null;
        return authorizationHeader.replace("Bearer ", "");
    }
}
