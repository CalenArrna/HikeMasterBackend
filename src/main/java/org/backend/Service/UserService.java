package org.backend.Service;


import org.backend.Model.Authority;
import org.backend.Model.HikeMasterUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    EntityManager em;


    @Override
    public UserDetails loadUserByUsername(String name) {
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

    public List<HikeMasterUser> loginUser(String username, String password){
        List<HikeMasterUser> hikeMasterUser = em.createQuery("SELECT u FROM HikeMasterUser u WHERE u.username= :username AND u.password= :password", HikeMasterUser.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        if(!hikeMasterUser.isEmpty()){
            return hikeMasterUser;
        }else {
            return null;
        }
    }
    public Authority getUserAuthority(){
        return em.createQuery("select a FROM Authority a WHERE a.roleName='ADMIN'",Authority.class).getSingleResult();
    }

}