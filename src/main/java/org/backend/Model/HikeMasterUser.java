package org.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;

@Entity
@Component
public class HikeMasterUser implements UserDetails {
    @Id
    @GeneratedValue
    private long id;
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
    private Set<Authority> authoritySet = new HashSet<>();
    @Column
    private boolean isDeactivated;
    @Column
    private boolean notification;
    @JsonIgnore
    @OneToMany
    private List<Message> userMessageList = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Message> getUserMessageList() {
        return userMessageList;
    }

    public void setUserMessageList(List<Message> userMessageList) {
        this.userMessageList = userMessageList;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Authority> getAuthorityList() {
        return authoritySet;
    }

    public void setAuthorityList(Set<Authority> authorityList) {
        this.authoritySet = authorityList;
    }

//    public GrantedAuthority setAuthority(String authority) {
//        return new SimpleGrantedAuthority(authority);
//    }


    public Set<Authority> getAuthoritySet() {
        return authoritySet;
    }

    public void setAuthoritySet(Set<Authority> authoritySet) {
        this.authoritySet = authoritySet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//    public String getUsername(){
//        return username;
//    }

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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(role));
        return list;
    }
}
