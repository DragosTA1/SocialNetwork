package com.example.lab7_map_2.Controller;

import com.example.lab7_map_2.Service.FriendshipService;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("ALL")
public class SocialNetworkController {
    private UserService userService;
    private FriendshipService friendshipService;

    public void setService(UserService userSev, FriendshipService friendshipSev) {
        userService = userSev;
        friendshipService = friendshipSev;
    }

    @FXML
    private void initialize() {
    }


    public void handleLogin(ActionEvent actionEvent) {
        try {
            // create a new stage for Login.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApplication.class.getResource("Login-view.fxml"));

            AnchorPane root = loader.load();

            // Create the Stage.
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.initModality(Modality.WINDOW_MODAL);
            Scene loginScene = new Scene(root);
            loginStage.setScene(loginScene);

            LoginController controller = loader.getController();
            controller.setService(userService, friendshipService, loginStage);


            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSignIN(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApplication.class.getResource("SignIn-view.fxml"));

            AnchorPane root = loader.load();

            Stage signInStage = new Stage();
            signInStage.setTitle("Sign In");
            signInStage.initModality(Modality.WINDOW_MODAL);
            Scene signInScene = new Scene(root);
            signInStage.setScene(signInScene);

            SignInController controller = loader.getController();
            controller.setService(userService, friendshipService, signInStage);

            signInStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAdminLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApplication.class.getResource("LoginAsAdmin-view.fxml"));

            AnchorPane root = loader.load();

            Stage logAdminStage = new Stage();
            logAdminStage.setTitle("Login As Admin");
            logAdminStage.initModality(Modality.WINDOW_MODAL);
            Scene logAdminScene = new Scene(root);
            logAdminStage.setScene(logAdminScene);

            LoginAsAdminController controller = loader.getController();
            controller.setService(userService, friendshipService, logAdminStage);

            logAdminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
