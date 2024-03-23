package com.example.lab7_map_2.Controller;

import com.example.lab7_map_2.Service.FriendshipService;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.StartApplication;
import com.example.lab7_map_2.Validator.ValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginAsAdminController{
    @FXML
    private TextField textFieldAdminPassword;
    private final String adminPassword = "AdminPassword2023!";
    private UserService userService;
    private FriendshipService friendshipService;

    private Stage loginAdminStage;
    public void setService(UserService userSev, FriendshipService friendshipSev, Stage st) {
        userService = userSev;
        friendshipService = friendshipSev;
        loginAdminStage = st;
    }

    public void handleLogin() {
        //Save the user
        String password = textFieldAdminPassword.getText();
        try {
            if (!Objects.equals(adminPassword, password)) {
                //incorect password
                MessageAlert.showErrorMessage(null, "Invalid password!");
            } else {
                //corect password
                loginAdminStage.close();
                //open admin profile
                try {
                    // create a new stage for Admin profile.
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(StartApplication.class.getResource("Admin-view.fxml"));

                    AnchorPane root = loader.load();

                    // Create the Stage.
                    Stage stage = new Stage();
                    stage.setTitle("Admin account");
                    stage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);


                    AdminController Controller = loader.getController();
                    Controller.setService(userService, friendshipService, stage);

                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (ValidationException | IllegalArgumentException ve) {
            MessageAlert.showErrorMessage(null, ve.getMessage());
        }
    }
}
