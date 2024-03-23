package com.example.lab7_map_2.Service;

import com.example.lab7_map_2.Domain.*;
import com.example.lab7_map_2.Repository.MessageDBRepository;
import com.example.lab7_map_2.Repository.UserDBRepository;
import com.example.lab7_map_2.Validator.MessageValidator;
import com.example.lab7_map_2.Validator.Validator;
import com.example.lab7_map_2.utils.events.ChangeEventType;
import com.example.lab7_map_2.utils.events.Event;
import com.example.lab7_map_2.utils.events.MessageChangeEvent;
import com.example.lab7_map_2.utils.observer.Observable;
import com.example.lab7_map_2.utils.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MessageService implements Observable<Event> {
    private final List<Observer<Event>> observers = new ArrayList<>();

    private final UserDBRepository userRepo;
    private final MessageDBRepository messageRepo;
    private final Validator<Message> messageValidator;

    public MessageService(UserDBRepository userRepo, MessageDBRepository messageRRepo, Validator<Message> val) {
        this.userRepo = userRepo;;
        this.messageRepo = messageRRepo;
        this.messageValidator = val;
    }

    public void sendAMessage(Long from, List<Long> to, String message, Long replied) {
        Message mess = new Message(from, to, message, LocalDate.now(), replied);
        messageValidator.validate(mess);
        messageRepo.add(mess);
        notifyObservers(new MessageChangeEvent(ChangeEventType.ADD, null, mess));
    }

    public Iterable<User> findAllWhoHaveAChatWithUser(Long idUser) {
        List<User> friendsWithChat = new ArrayList<>();
        Iterable<Long> usersID = messageRepo.findAllWhoHaveAChatWithUser(idUser);
        usersID.forEach(idFriend -> {
            User userFriend = userRepo.findOne(idFriend).get();
            friendsWithChat.add(userFriend);
        });
        return friendsWithChat;
    }
    public Iterable<MessageDTO> findAllMessagesBetweenTwoUsers(Long idUser, Long idFriend) {
        return messageRepo.findAllMessagesBetweenTwoUsers(idUser, idFriend);
    }
    @Override
    public void addObserver(Observer<Event> e) {observers.add(e);}

    @Override
    public void removeObserver(Observer<Event> e) {observers.remove(e);}

    @Override
    public void notifyObservers(Event t) {observers.forEach(o -> o.update(t));}
}
