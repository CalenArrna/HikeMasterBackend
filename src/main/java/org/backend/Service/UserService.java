package org.backend.Service;


import org.backend.Model.User;
import org.dozer.DozerBeanMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;


@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String name) {

        return null;
    }

    public boolean userExists(String name) {
        User user = em.createQuery("select u from User u where u.username = :lookFor", User.class)
                .setParameter("lookFor",name)
                .getSingleResult();
        return user != null;
    }

    @Transactional
    public long addUserToDatabase(User user) {
        em.persist(user);
        long id = 1;
        return id;
    }
    
    

}