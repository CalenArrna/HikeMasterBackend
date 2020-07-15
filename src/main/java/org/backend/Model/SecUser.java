package org.backend.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class SecUser {
    @Id
    @GeneratedValue
    private long user_id;
    @Column(name = "role")
    private String role;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String userName;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "password")
    private String password;
    @ManyToMany
    private Set<Authority> authorities = new HashSet<>();
    @Column(name = "isdeactivate")
    private boolean isDeactivated;
    @Column(name = "notification")
    private boolean notification;

    public long getId() {
        return user_id;
    }

    public void setId(long id) {
        this.user_id = id;
    }

    public void setUsername(String username) {
        username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public boolean isDeactivated() {
        return isDeactivated;
    }

    public void setDeactivated(boolean deactivated) {
        isDeactivated = deactivated;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public String getRole() {
        return role;
    }

    public Set<Authority> getAuthorityList() {
        return authorities;
    }

    public void setAuthorityList(Set<Authority> authorityList) {
        this.authorities = authorityList;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public GrantedAuthority setAuthority(String authority) {

        return new SimpleGrantedAuthority(authority);
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}