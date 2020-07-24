package org.backend.Service;

import org.backend.Model.HikeMasterUser;
import org.backend.Model.HikeRoute;
import org.backend.Model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager em;

   // @Autowired
   // public MessageService(EntityManager em) {
   //     this.em = em;
   // }

    @Transactional
    public List<Message> addMessage(Message message, Long routeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) principal;
            message.setHikeMasterUser((HikeMasterUser) oidcUser);

        } else if (principal instanceof HikeMasterUser) {
            HikeMasterUser hikeMasterUser = (HikeMasterUser) principal;
            message.setHikeMasterUser(hikeMasterUser);
        }
        addMessageToMessageList(message, routeId);
        em.persist(message);
        return getRouteMessageList(routeId);
    }

    public List<Message> getRouteMessageList(Long routeId){
       return em.createQuery("select h from HikeRoute h where h.routeId = :routeId", HikeRoute.class)
                .setParameter("routeId", routeId)
                .getResultList().get(0).getMessages();
    }

    @Transactional
    public void addMessageToMessageList(Message message, Long routeId){
        em.createQuery("select h from HikeRoute h where h.routeId = :routeId", HikeRoute.class)
                .setParameter("routeId", routeId)
                .getResultList().get(0).getMessages().add(message);
    }

}
