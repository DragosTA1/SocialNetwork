package com.example.lab7_map_2.Controller;

import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Service.FriendshipService;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.StartApplication;
import com.example.lab7_map_2.Validator.ValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldPassword;

    private UserService userService;
    private FriendshipService friendshipService;
    private Stage loginStage;

    public void setService(UserService userSev, FriendshipService friendshipSev, Stage st) {
        userService = userSev;
        friendshipService = friendshipSev;
        loginStage = st;
    }

    @FXML
    private void initialize() {
    }

    public void handleLogin() {
        //Save the user
        Long ID = Long.parseLong(textFieldID.getText());
        String password = textFieldPassword.getText();
        try {
            User user = userService.findOne(ID);
            if (!Objects.equals(user.getPassword(), password)) {
                //incorect password
                MessageAlert.showErrorMessage(null, "Invalid password!");
            } else {
                //corect password
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Success Login", "Welcome to your account");
                //close the LoginStage
                loginStage.close();
                //open user profile
                try {
                    // create a new stage for Login.
                    // create a new stage for User profile.
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(StartApplication.class.getResource("User-view.fxml"));

                    AnchorPane root = loader.load();

                    UserController userController = loader.getController();
                    userController.setService(userService, friendshipService);

                    // Create the Stage.
                    Stage userStage = new Stage();
                    userStage.setTitle("Your account");
                    userStage.initModality(Modality.WINDOW_MODAL);
                    Scene userScene = new Scene(root);
                    userStage.setScene(userScene);

                    userStage.show();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (ValidationException | IllegalArgumentException ve) {
            MessageAlert.showErrorMessage(null, ve.getMessage());
        }

    }
}
