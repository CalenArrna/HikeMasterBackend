package org.backend.Controllers;

import org.backend.DTOs.ResponseDTO;
import org.backend.Model.Message;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.MessageRepository;
import org.backend.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MessageController {

    MessageService messageService;
    HikeRouteRepository hikeRouteRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageService messageService, HikeRouteRepository hikeRouteRepository, MessageRepository messageRepository) {
        this.messageService = messageService;
        this.hikeRouteRepository = hikeRouteRepository;
        this.messageRepository = messageRepository;
    }

    @RequestMapping(value = "/hike_route/{route_Id}/messages", method = RequestMethod.POST)
    public ResponseDTO addMessageToRoute(@PathVariable Long route_Id, @RequestBody Message message) {
        return messageService.addCommentToRoute(route_Id, message);
    }

    @GetMapping(value = "/hike_route/{route_Id}/messages")
    public List<Message> getMessages(@PathVariable Long route_Id) {
        if (hikeRouteRepository.findById(route_Id).isPresent()) {
            return hikeRouteRepository.findById(route_Id).get().getMessages();
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "/hike_route/{message_Id}/delete_message")
    public ResponseDTO deleteMessage(@PathVariable Long message_Id) {
        return messageService.deleteMessage(message_Id);
    }

    @GetMapping(value = "/messages")
    public List<Message> getEveryMessage() {
        return messageRepository.findAll();
    }

    @PostMapping(value = "/hike_route/{message_Id}/alter_message")
    public Message alterMessage(@PathVariable Long message_Id, @RequestBody String text) {
        return messageService.alterMessage(message_Id, text);
    }
}
