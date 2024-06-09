package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.config.TokenService;
import com.K1dou.Loja.virtual.model.Dtos.TokenDto;
import com.K1dou.Loja.virtual.model.Dtos.UserLogin;
import com.K1dou.Loja.virtual.model.Usuario;
import com.K1dou.Loja.virtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity loginUsuario(@RequestBody UserLogin dto) {

        var usuario = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());

        var auth = authenticationManager.authenticate(usuario);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDto(token));
    }

    @GetMapping("/buscarPorLogin/{login}")
    public ResponseEntity<Usuario> findByLogin(@PathVariable String login) {

        Usuario usuario = usuarioRepository.findByLogin(login);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("Buscar/Por/{id}")
    public ResponseEntity<Usuario> findByLogin(@PathVariable Long id) {


        Usuario usuario = usuarioRepository.findById(id).get();


        return ResponseEntity.ok(usuario);
    }


}
