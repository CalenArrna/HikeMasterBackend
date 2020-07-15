package org.backend.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.persistence.*;
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
    private String username;
    @Column
    private String fullName;
    @Column
    private String password;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static String getRolePrefix() {
        return ROLE_PREFIX;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

/*    public String getUsername() {
        return username;
    }*/

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
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
        return username;
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
