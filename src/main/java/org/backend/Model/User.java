package org.backend.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Component
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;
    private static final String ROLE_PREFIX="ROLE_";
    @Column
    private String role;
    @Column
    private String email;
    @Column
    private String dateOfBirth;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private LocalDateTime loginDate;
    @ManyToMany
    private Set<Authority>authorities=new HashSet<>();

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

    public GrantedAuthority setAuthority(String authority){

        return new SimpleGrantedAuthority(authority);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
