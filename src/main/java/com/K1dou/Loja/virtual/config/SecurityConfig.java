package com.K1dou.Loja.virtual.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/acesso/**").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/buscarPorLogin/**").hasRole("ADMIN")
                        .requestMatchers("/pessoa/**").permitAll()
                        .requestMatchers("/categoriaProduto/**").permitAll()
                        .requestMatchers("/produto/**").permitAll()
                        .requestMatchers("/marcaProduto/**").permitAll()
                        .requestMatchers("/contaPagar/**").permitAll()
                        .requestMatchers("/notaFiscal/**").permitAll()
                        .requestMatchers("/notaItemProduto/**").permitAll()
                        .requestMatchers("/imagemProduto/**").permitAll()
                        .requestMatchers("/avaliacaoProduto/**").permitAll()
                        .requestMatchers("/formaPagamento/**").permitAll()
                        .requestMatchers("/vendaCompra/**").permitAll()
                        .requestMatchers("/usuario/**").permitAll()
                        .requestMatchers("/statusRastreio/**").permitAll()
                        .requestMatchers("/cupDesc/**").permitAll()
                        .requestMatchers("/Loja-virtual/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/index","/pagamento/**","/resources/**","/static;**","/templates/**","classpath:/**","webjars/**","/WEB-INF/classes/static/**").permitAll()

                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
