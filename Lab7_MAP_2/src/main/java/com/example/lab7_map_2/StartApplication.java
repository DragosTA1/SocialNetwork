package com.example.lab7_map_2;

import com.example.lab7_map_2.Controller.SocialNetworkController;
import com.example.lab7_map_2.Domain.Friendship;
import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Repository.FriendshipDBRepository;
import com.example.lab7_map_2.Repository.FriendshipRepository;
import com.example.lab7_map_2.Repository.Repository;
import com.example.lab7_map_2.Repository.UserDBRepository;
import com.example.lab7_map_2.Service.FriendshipService;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.Validator.FriendshipValidator;
import com.example.lab7_map_2.Validator.UserValidator;
import com.example.lab7_map_2.Validator.Validator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {

    private Repository<Long, User> userRepo;
    private FriendshipRepository friendshipRepo;
    private Validator<User> userValidator;
    private Validator<Friendship> friendshipValidator;
    private UserService userService;
    private FriendshipService friendshipService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        userRepo = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "1234qwerD");
        userValidator = new UserValidator();
        friendshipRepo = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "1234qwerD");
        friendshipValidator = new FriendshipValidator();
        userService = new UserService(userRepo, friendshipRepo, userValidator);
        friendshipService = new FriendshipService(friendshipRepo, userRepo, friendshipValidator);


        initView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();


    }

    private void initView(Stage primaryStage) throws IOException {

        FXMLLoader applicationLoader = new FXMLLoader();
        applicationLoader.setLocation(getClass().getResource("SocialNetwork-view.fxml"));
        AnchorPane ApplicationLayout = applicationLoader.load();
        primaryStage.setScene(new Scene(ApplicationLayout));

        SocialNetworkController socialNetworkController = applicationLoader.getController();
        socialNetworkController.setService(userService, friendshipService);

    }
}