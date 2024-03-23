package com.example.lab7_map_2.Service;

import com.example.lab7_map_2.Domain.Tuple;
import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Repository.FriendshipRepository;
import com.example.lab7_map_2.Repository.Repository;
import com.example.lab7_map_2.Validator.Validator;
import com.example.lab7_map_2.utils.events.ChangeEventType;
import com.example.lab7_map_2.utils.events.UserChangeEvent;
import com.example.lab7_map_2.utils.observer.Observable;
import com.example.lab7_map_2.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserService implements Observable<UserChangeEvent> {
    private final Repository<Long, User> userRepo;
    private final FriendshipRepository friendshipRepo;
    private final Validator<User> val;

    private final List<Observer<UserChangeEvent>> observers = new ArrayList<>();


    public UserService(Repository<Long, User> repo, FriendshipRepository friendshipRepo, Validator<User> val) {
        this.userRepo = repo;
        this.friendshipRepo = friendshipRepo;
        this.val = val;
    }


    public User findOne(Long id) {
        Optional<User> u_op = userRepo.findOne(id);
        if (u_op.isEmpty()) {
            throw new IllegalArgumentException("There is no user with the entered ID.");
        }
        else return u_op.get();
    }

    public Iterable<User> getAll() {
        return userRepo.getAll();
    }


    public void add(String firstName, String lastName, String password) {
        User u = new User(firstName, lastName, password);
        val.validate(u);
        Optional<User> u_op = userRepo.add(u);
        if (u_op.isPresent()) {
            throw new IllegalArgumentException("Is there already a user with this ID! \n " + u_op.get());
        }
        else {
            notifyObservers(new UserChangeEvent(ChangeEventType.ADD, null, u));
        }
    }


    public User delete(Long id) {
        Optional<User> userToDelete_optional = userRepo.findOne(id);
        if (userToDelete_optional.isEmpty())
            throw new IllegalArgumentException("There no user with this ID! \n");
        else {
            User userToDelete =  userToDelete_optional.get();
            List<Long> friendships = (List<Long>) friendshipRepo.findFriends(userToDelete.getId());
            userRepo.delete(id);
            if (friendships != null) {
                for (Long idFriend : friendships) {
                    friendshipRepo.delete(new Tuple<>(id, idFriend));
                    friendshipRepo.delete(new Tuple<>(idFriend, id));
                }
            }
            notifyObservers(new UserChangeEvent(ChangeEventType.DELETE, userToDelete,null ));
            return userToDelete;
        }
    }


    public void update(Long id, String firstName, String lastName, String password) {
        User new_u = new User(firstName, lastName, password);
        new_u.setId(id);
        val.validate(new_u);
        Optional<User> u_op = userRepo.update(new_u);
        if (u_op.isEmpty()) {
            throw new IllegalArgumentException("There no user with this ID! \n");
        }
        else {
            notifyObservers(new UserChangeEvent(ChangeEventType.UPDATE, u_op.get(), new_u));
        }
    }


    @Override
    public void addObserver(Observer<UserChangeEvent> e) { observers.add(e); }

    @Override
    public void removeObserver(Observer<UserChangeEvent> e) {observers.remove(e);}

    @Override
    public void notifyObservers(UserChangeEvent t) { observers.forEach(o -> o.update(t)); }
}
