package org.backend.Service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
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

        return true;
    }


}