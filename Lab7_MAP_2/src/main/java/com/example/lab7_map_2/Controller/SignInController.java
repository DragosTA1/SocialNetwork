package com.example.lab7_map_2.Controller;

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

public class SignInController {
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldPassword;

    private UserService userService;
    private FriendshipService friendshipService;
    private Stage signInStage;

    public void setService(UserService userSev, FriendshipService friendshipSev, Stage st) {
        userService = userSev;
        friendshipService = friendshipSev;
        signInStage = st;
    }

    @FXML
    private void initialize() {
    }

    public void handleSignIn() {
        //Save the user
        String firstName = textFieldFirstName.getText( );
        String lastName = textFieldLastName.getText();
        String password = textFieldPassword.getText();

        try {
            userService.add(firstName, lastName,password);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Success SignIn", "Welcome to your account");

            //open user profile
            signInStage.close();
            try {
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

        } catch (ValidationException | IllegalArgumentException ve) {
            MessageAlert.showErrorMessage(null,ve.getMessage());
        }

    }
}
