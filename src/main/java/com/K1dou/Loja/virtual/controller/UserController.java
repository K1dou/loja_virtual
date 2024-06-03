//package com.K1dou.Loja.virtual.controller;
//
//import com.K1dou.Loja.virtual.config.TokenService;
//import com.K1dou.Loja.virtual.model.Acesso;
//import com.K1dou.Loja.virtual.model.Dtos.*;
//import com.K1dou.Loja.virtual.model.Pessoa;
//import com.K1dou.Loja.virtual.model.User;
//import com.K1dou.Loja.virtual.model.Usuario;
//import com.K1dou.Loja.virtual.repository.PessoaRepository;
//import com.K1dou.Loja.virtual.repository.UserRepository;
//import com.K1dou.Loja.virtual.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private TokenService tokenService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//
//    @PostMapping("/cadastro")
//    public ResponseEntity cadastroUsuario(@RequestBody UserRegister1 dto) {
//
//        var senha = passwordEncoder.encode(dto.password());
//
//        User usuario = new User(dto.username(), senha, dto.role());
//
//        userRepository.save(usuario);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity loginUsuario(@RequestBody UserLogin1 dto) {
//
//        var usuario = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
//
//        var auth = authenticationManager.authenticate(usuario);
//
//        var token = tokenService.generateToken((User) auth.getPrincipal());
//
//        return ResponseEntity.ok(new TokenDto(token));
//    }
//
//
//
//}
