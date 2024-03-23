package com.example.lab7_map_2.Controller;

import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Service.FriendshipService;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.StartApplication;
import com.example.lab7_map_2.utils.events.UserChangeEvent;
import com.example.lab7_map_2.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Entity;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdminController implements Observer<UserChangeEvent> {

    private UserService userService;
    private FriendshipService friendshipService;
    private Stage adminStage;

    ObservableList<User> usersModel = FXCollections.observableArrayList();
    ObservableList<User> friendshipsModel = FXCollections.observableArrayList();

    @FXML
    TableView<User> usersTableView;
    @FXML
    TableColumn<User, Long> usersTableColumnID;
    @FXML
    TableColumn<User,String> usersTableColumnFirstName;
    @FXML
    TableColumn<User,String> usersTableColumnLastName;
    @FXML
    TableColumn<User,String> usersTableColumnPassword;


    public void setService(UserService userSev, FriendshipService friendshipSev, Stage st) {
        userService = userSev;
        friendshipService = friendshipSev;
        adminStage = st;
        userService.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        usersTableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        usersTableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        usersTableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usersTableColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        usersTableView.setItems(usersModel);
    }

    private void initModel() {
        Iterable<User> users = userService.getAll();
        List<User> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        usersModel.setAll(usersList);
    }


    public void handleDeleteFriendship(ActionEvent actionEvent) {
    }

    public void handleFindFriendship(ActionEvent actionEvent) {
    }

    public void handleTheMostSociableComunity(ActionEvent actionEvent) {
    }

    public void handleNumberOfComunities(ActionEvent actionEvent) {
    }

    public void handleDeleteUser(ActionEvent actionEvent) {
        User selected = (User) usersTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            User deleted = userService.delete(selected.getId());
            if (null != deleted)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", selected + "has been successfully deleted.!");
        } else MessageAlert.showErrorMessage(null, "Select a user!");
    }

    public void handleFindUser(ActionEvent actionEvent) {
        try {
            // create a new stage to find a user.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApplication.class.getResource("FindUser-view.fxml"));

            AnchorPane root = loader.load();

            // Create the Stage.
            Stage stage = new Stage();
            stage.setTitle("Your account");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            FindUserController userController = loader.getController();
            userController.setService(userService, stage);

            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserChangeEvent userChangeEvent) {
        initModel();
    }
}
