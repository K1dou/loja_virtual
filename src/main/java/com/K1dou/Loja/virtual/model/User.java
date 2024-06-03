//package com.K1dou.Loja.virtual.model;
//
//import com.K1dou.Loja.virtual.enums.Role;
//import jakarta.persistence.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Entity
//@Table(name = "\"user\"")
//@SequenceGenerator(name = "seq_user",sequenceName = "seq_user",allocationSize = 1)
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_user")
//    private Long id;
//    @Column(name = "username")
//    private String username;
//    private String password;
//    @Enumerated
//    private Role role;
//
//
//    public User() {
//    }
//
//    public User(String username, String password, Role role) {
//        this.username = username;
//        this.password = password;
//        this.role = role;
//    }
//
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (role==Role.ADMIN)return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));
//        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//}
