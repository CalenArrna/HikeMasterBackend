package org.backend.Service;

import antlr.debug.MessageAdapter;
import org.backend.Model.Message;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager em;

    HikeRouteRepository hikeRouteRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(HikeRouteRepository hikeRouteRepository, MessageRepository messageRepository) {
        this.hikeRouteRepository = hikeRouteRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public String addMessage(Long routeId, Message message) {
        if (hikeRouteRepository.findById(routeId).isPresent()) {
            Message message1 = new Message();
            message1.setTitle(message.getTitle());
            message1.setText(message.getText());
            message1.setMessageDate(LocalDateTime.now());
            hikeRouteRepository.findById(routeId).get().getMessages().add(messageRepository.save(message1));
            return "success";
        } else {
            return "failed";
        }
    }

    /*
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

    public List<Message> getRouteMessageList(Long routeId) {
        return em.createQuery("select h from HikeRoute h where h.routeId = :routeId", HikeRoute.class)
                .setParameter("routeId", routeId)
                .getResultList().get(0).getMessages();
    }

    @Transactional
    public void addMessageToMessageList(Message message, Long routeId) {
        em.createQuery("select h from HikeRoute h where h.routeId = :routeId", HikeRoute.class)
                .setParameter("routeId", routeId)
                .getResultList().get(0).getMessages().add(message);
    }

     */

}
