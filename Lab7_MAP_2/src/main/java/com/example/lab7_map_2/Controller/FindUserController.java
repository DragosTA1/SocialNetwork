package com.example.lab7_map_2.Controller;

import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.Validator.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FindUserController {
    private UserService userService;
    private Stage findUserStage;

    @FXML
    private TextField textFieldID;

    public void setService(UserService userSev, Stage st) {
        userService = userSev;
        findUserStage = st;
    }

    public void handleFindOne() {
        Long id = Long.parseLong(textFieldID.getText());
        try {
            User user = userService.findOne(id);
            MessageAlert.showMessage(findUserStage, Alert.AlertType.INFORMATION, "User found", "the searched user is: " + user);
        } catch (ValidationException | IllegalArgumentException ve) {
            MessageAlert.showErrorMessage(findUserStage, ve.getMessage());
        }
    }
}
