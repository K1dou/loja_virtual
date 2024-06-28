package com.K1dou.Loja.virtual.controller;


import com.K1dou.Loja.virtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

   @Autowired
   private UsuarioRepository usuarioRepository;

   @DeleteMapping("/deleteUsuario/{id}")
   public ResponseEntity<?>deleteUsuario(@PathVariable Long id){

       usuarioRepository.deleteUsuario(id);
       return new ResponseEntity<>("Usuario deletado", HttpStatus.OK);
   }
}
