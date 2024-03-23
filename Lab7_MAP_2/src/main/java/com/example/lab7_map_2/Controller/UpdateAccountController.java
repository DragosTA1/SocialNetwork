package com.example.lab7_map_2.Controller;

import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.Validator.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;


public class UpdateAccountController{
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldPassword;

    private String userEmail;
    private TabPane userTabPane;
    private Tab updateTab;

    private UserService userService;

    public void setService(UserService userSev){
        userService = userSev;
    }

    public void setTab(TabPane mainTabPane, Tab myTab){
        userTabPane = mainTabPane;
        updateTab = myTab;
    }

    public void setUser(String email) {
        userEmail = email;
    }

    @FXML
    private void initialize() {
    }
    public void handleUpdate() {
        String firstName = textFieldFirstName.getText( );
        String lastName = textFieldLastName.getText();
        String password = textFieldPassword.getText();

        if (firstName.isEmpty() && lastName.isEmpty()  && password.isEmpty()) {
            MessageAlert.showErrorMessage(null,  "Fill in at least one of the fields above!");
            return;
        }

        User user = userService.findOne(userEmail);
        if (firstName.isEmpty()) firstName = user.getFirstName();
        if (lastName.isEmpty()) lastName = user.getLastName();
        if (password.isEmpty()) password = user.getPassword();

        try {
            userService.update(user.getId(), firstName, lastName, userEmail, password);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes", "Your account was succesfully update");
            userTabPane.getTabs().remove(updateTab);
        } catch (ValidationException | IllegalArgumentException ve) {
            MessageAlert.showErrorMessage(null,ve.getMessage());
        }
    }
}
