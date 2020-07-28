package org.backend.Model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String roleName;
    @ManyToMany(mappedBy = "authoritySet",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<HikeMasterUser> securityHikeMasterUsers = new HashSet<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<HikeMasterUser> getSecurityHikeMasterUsers() {
        return securityHikeMasterUsers;
    }

    public void setSecurityHikeMasterUsers(Set<HikeMasterUser> securityHikeMasterUsers) {
        this.securityHikeMasterUsers = securityHikeMasterUsers;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}

