package org.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    public MessageService(EntityManager em) {
        this.em = em;
    }


}
