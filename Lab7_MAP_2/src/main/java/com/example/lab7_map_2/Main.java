package com.example.lab7_map_2;

import com.example.lab7_map_2.Domain.Friendship;
import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Repository.FriendshipDBRepository;
import com.example.lab7_map_2.Repository.UserDBRepository;
import com.example.lab7_map_2.Service.FriendshipService;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.UI.UI;
import com.example.lab7_map_2.Validator.FriendshipValidator;
import com.example.lab7_map_2.Validator.UserValidator;
import com.example.lab7_map_2.Validator.Validator;

public class Main {
    public static void main(String[] args) {
        Validator<User> userValidator = new UserValidator();
        Validator<Friendship> friendshipValidator =  new FriendshipValidator();
        //UserMemoryRepository userRepo = new UserMemoryRepository();
        UserDBRepository userDBRepo = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "1234qwerD");
        //FriendshipMemoryRepository friendshipRepo = new FriendshipMemoryRepository();
        FriendshipDBRepository friendshipDBRepo = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "1234qwerD");
        UserService userService = new UserService(userDBRepo, friendshipDBRepo, userValidator);
        FriendshipService friendshipService = new FriendshipService(friendshipDBRepo, userDBRepo, friendshipValidator);
        UI ui = new UI(userService, friendshipService);
        ui.run();
    }
}
