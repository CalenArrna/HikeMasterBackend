package org.backend.Service;

import org.backend.Model.Authority;
import org.backend.Model.HikeMasterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

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

    public List<HikeMasterUser> loginUser(String username, String password) {
        List<HikeMasterUser> hikeMasterUser = em.createQuery("SELECT u FROM HikeMasterUser u WHERE u.username= :username", HikeMasterUser.class)
                .setParameter("username", username)
                .getResultList();

        if (!hikeMasterUser.isEmpty()) {
            hikeMasterUser.get(0).setPassword(passwordEncoder.encode(password));
            return hikeMasterUser;
        } else {
            return null;
        }
    }

    public Authority getUserAuthority() {
        return em.createQuery("select a FROM Authority a WHERE a.roleName='ADMIN'", Authority.class).getSingleResult();
    }


}