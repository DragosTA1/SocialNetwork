package com.example.lab7_map_2.utils.events;

import com.example.lab7_map_2.Domain.FriendRequest;
import com.example.lab7_map_2.Domain.Message;

public class MessageChangeEvent implements Event {
    private ChangeEventType type;
    private Message oldMessage;
    private Message newMessage;

    public MessageChangeEvent(ChangeEventType type, Message old_m, Message new_m) {
        this.type = type;
        this.oldMessage = old_m;
        this.newMessage = new_m;
    }


    public ChangeEventType getType() {
        return type;
    }
    public Message getOldMessage() {
        return oldMessage;
    }
    public Message getNewMessage() { return newMessage; }


    public void setType(ChangeEventType type) { this.type = type; }
    public void setOldUser(Message oldFriendship) { this.oldMessage = oldMessage; }
    public void setNewUser(Message newFriendship) { this.newMessage = newMessage;}
}
