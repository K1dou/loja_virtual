//package com.K1dou.Loja.virtual.service;
//
//import com.K1dou.Loja.virtual.repository.UserRepository;
//import jakarta.persistence.Access;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username);
//    }
//}
