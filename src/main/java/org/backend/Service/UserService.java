package org.backend.Service;

import org.backend.Model.Authority;
import org.backend.Model.HikeMasterUser;
import org.backend.Repository.HikeMasterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
public class UserService implements UserDetailsService {

    // @Autowired
    // PasswordEncoder passwordEncoder;



    @PersistenceContext
    EntityManager em;


    @Override
    public UserDetails loadUserByUsername(String name) {
        try {
            return em.createQuery("select u from HikeMasterUser u where u.username = :name", HikeMasterUser.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public String getRoleOfUser(HikeMasterUser hikeMasterUser) {
        if (hikeMasterUser.getRole().equals("USER")) {
            return "USER";
        } else if (hikeMasterUser.getRole().equals("ADMIN")) {
            return "ADMIN";
        } else {
            return "ANONYMUS";
        }
    }

    public String getHikeMasterUser() {
        String name = getUserName();
        if (name != null){
            return em.createQuery("select u from HikeMasterUser u where u.username = :name", HikeMasterUser.class)
                    .setParameter("name", name)
                    .getResultList()
                    .get(0).getRole();
        }
        return null;
    }

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

    @Transactional
    public boolean userExists(String name) {
        return !em.createQuery("select u from HikeMasterUser u where u.username = :lookFor", HikeMasterUser.class)
                .setParameter("lookFor", name)
                .getResultList().isEmpty();
    }

    @Transactional
    public void addUserToDatabase(HikeMasterUser hikeMasterUser) {
        em.persist(hikeMasterUser);
    }


    public Authority getUserAuthority() {
        return em.createQuery("select a FROM Authority a WHERE a.roleName='USER'", Authority.class).getSingleResult();
    }

    @Transactional
    public boolean isEmailExists(String email) {
        return !em.createQuery("select u from HikeMasterUser u where u.email = :lookFor", HikeMasterUser.class)
                .setParameter("lookFor", email)
                .getResultList().isEmpty();
    }
}