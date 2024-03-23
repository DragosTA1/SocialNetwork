package com.example.lab7_map_2.Controller;

import com.example.lab7_map_2.Domain.MessageDTO;
import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Service.MessageService;
import com.example.lab7_map_2.Service.UserService;
import com.example.lab7_map_2.StartApplication;
import com.example.lab7_map_2.Validator.ValidationException;
import com.example.lab7_map_2.utils.events.Event;
import com.example.lab7_map_2.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ChatController implements Observer<Event> {
    @FXML
    private Label friendNameLabel;
    @FXML
    private TextField newMessageTextField;
    @FXML
    private ListView<User> usersListView;
    @FXML
    private ListView<MessageDTO> messagesListView;
    @FXML
    private Button sendButton;

    ObservableList<User> usersModel = FXCollections.observableArrayList();
    ObservableList<MessageDTO> messageModel = FXCollections.observableArrayList();

    private User selectedUser;
    private List<MessageDTO> currentChatMessageList;


    // Login user's data
    private Long idUser;


    //Service
    private UserService userService;
    private MessageService messageService;

    //Tab Pane and currrent Tab
    private TabPane socialNetworkTabPane;
    private Tab currentTab;

    public void setService(UserService userSev, MessageService messSev) {
        userService = userSev;
        messageService = messSev;
        userService.addObserver(this);
        messageService.addObserver(this);
    }


    public void setTab(TabPane mainTabPane, Tab tab){
        socialNetworkTabPane = mainTabPane;
        currentTab = tab;
    }


    public void setUser(Long idU) {
        idUser = idU;
        initModel();
    }


    @FXML
    public void initialize() {
        usersListView.setItems(usersModel);
        usersListView.setCellFactory(user -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getFirstName() +' '+ user.getLastName());
                }
            }
        });

        usersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
            if(newValue != null) {
                sendButton.setDisable(false);
                selectedUser = newValue;
                friendNameLabel.setText(selectedUser.getFirstName() + " " + selectedUser.getLastName());
                initChat();
            }
        });

        sendButton.setDisable(true);
        messagesListView.setItems(messageModel);
        messagesListView.setCellFactory(message -> new ListCell<>(){
            @Override
            protected void updateItem(MessageDTO message, boolean empty) {
                super.updateItem(message, empty);
                if (empty || message == null) {
                    setText(null);
                } else {
                    setText(message.getMessage());
                    if (message.getFrom().equals(idUser)) {
                        setTextFill(Color.GREEN);
                        setAlignment(Pos.CENTER_RIGHT);
                    }
                    else {
                        setAlignment(Pos.CENTER_LEFT);
                    }
                    setTooltip(new Tooltip(message.getData().toString()));
                }
            }
        });
    }


    private void initModel() {
        initUsers();
        initChat();
    }

    private void initUsers() {
        Iterable<User> users = messageService.findAllWhoHaveAChatWithUser(idUser);
        List<User> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        usersModel.setAll(usersList);
    }

    private void initChat() {
        if (selectedUser != null) {
            Iterable<MessageDTO> messages = messageService.findAllMessagesBetweenTwoUsers(idUser, selectedUser.getId());
            currentChatMessageList = StreamSupport.stream(messages.spliterator(), false)
                    .collect(Collectors.toList());
            messageModel.setAll(currentChatMessageList);
        }
    }


    @Override
    public void update(Event event) {
        initModel();
    }


    public void handleNewMessage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApplication.class.getResource("NewMessage-view.fxml"));
            AnchorPane root = loader.load();

            Tab newMessageTab = new Tab("Chats");
            newMessageTab.setContent(root);
            socialNetworkTabPane.getTabs().add(newMessageTab);
            socialNetworkTabPane.getSelectionModel().select(newMessageTab);

            NewMessageController controller = loader.getController();
            controller.setService(messageService, userService);
            controller.setTab(socialNetworkTabPane, newMessageTab);
            controller.setUser(idUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendHandler() {
        try {
            messageService.sendAMessage(idUser, Collections.singletonList(selectedUser.getId()), newMessageTextField.getText(), null);
            newMessageTextField.clear();
        } catch (ValidationException err) {
            MessageAlert.showErrorMessage(null, err.getMessage());
        }
    }
}
