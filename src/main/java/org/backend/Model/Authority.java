package org.backend.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Authority {
    @Id
    @GeneratedValue
    private long authority_id;
    @Column
    private String roleName;
    @ManyToMany(mappedBy = "authorities",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SecUser> securityUsers= new HashSet<>();


    public long getId() {
        return authority_id;
    }

    public void setId(long id) {
        this.authority_id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<SecUser> getSecurityUsers() {
        return securityUsers;
    }

    public void setSecurityUsers(Set<SecUser> securityUsers) {
        this.securityUsers = securityUsers;
    }
}

