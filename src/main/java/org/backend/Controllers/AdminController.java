package org.backend.Controllers;

import Repository.MessageRepository;
import org.backend.Model.Message;
import org.backend.Service.HikeRouteService;
import org.backend.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController implements MessageRepository {
    HikeRouteService hikeRouteService;
    MessageService messageService;

    @Autowired
    public AdminController(HikeRouteService hikeRouteService, MessageService messageService) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;
    }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public List<Message> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Message> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    @DeleteMapping("/{hikerouteId}")
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Message message) {

    }

    @Override
    public void deleteAll(Iterable<? extends Message> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Message> S save(S s) {
        return null;
    }

    @Override
    public <S extends Message> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Message> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Message> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Message> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Message getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Message> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Message> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Message> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Message> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Message> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Message> boolean exists(Example<S> example) {
        return false;
    }
}
