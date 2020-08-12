package org.backend.Service;

import org.backend.DTOs.ResponseDTO;
import org.backend.DTOs.WishListErrorDTO;
import org.backend.DTOs.WishRouteSuccessDTO;
import org.backend.Model.Authority;
import org.backend.Model.HikeMasterUser;
import org.backend.Model.HikeRoute;
import org.backend.Repository.HikeMasterUserRepository;
import org.backend.Repository.HikeRouteRepository;
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
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    HikeRouteRepository hikeRouteRepository;

    @Autowired
    HikeMasterUserRepository hikeMasterUserRepository;


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


    public String getHikeMasterUser() {
        String name = getUserName();
        if (name != null) {
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
        List<Authority> resultList = em.createQuery("select a FROM Authority a WHERE a.roleName='ADMIN'", Authority.class).getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean isEmailExists(String email) {
        return !em.createQuery("select u from HikeMasterUser u where u.email = :lookFor", HikeMasterUser.class)
                .setParameter("lookFor", email)
                .getResultList().isEmpty();
    }

    @Transactional
    public ResponseDTO addRouteToWishList(Long route_Id) {
        if (hikeRouteRepository.findById(route_Id).isPresent()) {
            HikeRoute hikeRoute = hikeRouteRepository.findById(route_Id).get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            HikeMasterUser hikeMasterUser = (HikeMasterUser) principal;
            hikeRoute.getWisherUsers().add(hikeMasterUser);
            hikeMasterUser.getHikeRouteWishSet().add(hikeRoute);
            hikeRouteRepository.save(hikeRoute);

            return new WishRouteSuccessDTO(hikeRoute);
        }
        return new WishListErrorDTO();
    }

}