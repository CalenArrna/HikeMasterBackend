package org.backend.Service;

import org.backend.Model.HikeMasterUser;
import org.backend.Model.HikeRoute;
import org.backend.Model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    public MessageService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public List<Message> addMessage(Message message, HikeRoute hikeRoute) {
       // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       // Object principal = authentication.getPrincipal();

       //  if (principal instanceof HikeMasterUser) {
            HikeMasterUser hikeMasterUser = new HikeMasterUser(); //(HikeMasterUser) principal;
            message.setHikeMasterUser(hikeMasterUser);
            message.setHikeRoute(hikeRoute);
            hikeRoute.getMessages().add(message);
       // }
        em.persist(message);
        return hikeRoute.getMessages();
    }


}
