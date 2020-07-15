package org.backend.Service;


import org.backend.Model.HikeMasterUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String name) {

        return null;
    }

    public boolean userExists(String name) {
        HikeMasterUser hikeMasterUser = em.createQuery("select u from HikeMasterUser u where u.username = :lookFor", HikeMasterUser.class)
                .setParameter("lookFor",name)
                .getSingleResult();
        return hikeMasterUser != null;
    }

    @Transactional
    public long addUserToDatabase(HikeMasterUser hikeMasterUser) {
        em.persist(hikeMasterUser);
        long id = 1;
        return id;
    }
    
    

}