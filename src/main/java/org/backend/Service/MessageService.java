package org.backend.Service;


import org.backend.DTOs.MessageErrorDTO;
import org.backend.DTOs.MessageSuccessDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.Model.HikeMasterUser;
import org.backend.Model.Message;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MessageService {


    HikeRouteRepository hikeRouteRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(HikeRouteRepository hikeRouteRepository, MessageRepository messageRepository) {
        this.hikeRouteRepository = hikeRouteRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public ResponseDTO addCommentToRoute(Long route_Id, Message message) {
        if (hikeRouteRepository.findById(route_Id).isPresent()) {
            message.setHikeRoute(hikeRouteRepository.findById(route_Id).get());
            message.setMessageDate(LocalDateTime.now());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();

            if (principal instanceof HikeMasterUser) {
                HikeMasterUser hikeMasterUser = (HikeMasterUser) principal;
                message.setUserName("Csaba");
                hikeMasterUser.getUserMessageList().add(message);
            }
            if (principal instanceof OidcUser) {
                OidcUser oidcUser = (OidcUser) principal;
                message.setUserName(oidcUser.getUserInfo().getNickName());
            }
            hikeRouteRepository.findById(route_Id).get().getMessages().add(message);
            messageRepository.save(message);
            return new MessageSuccessDTO();
        } else {
            return new MessageErrorDTO();
        }
    }

    @Transactional
    public ResponseDTO deleteMessage(Long messageId){
        if(messageRepository.findById(messageId).isPresent()){
            messageRepository.deleteById(messageId);
            return new MessageSuccessDTO();
        }else{
            return new MessageErrorDTO();
        }
    }

    @Transactional
    public Message alterMessage(Long messageId, Message message){
        if(messageRepository.findById(messageId).isPresent()){
            messageRepository.findById(messageId).get().setText(message.getText());
            return messageRepository.findById(messageId).get();
        }else {
            return null;
        }
    }
}
