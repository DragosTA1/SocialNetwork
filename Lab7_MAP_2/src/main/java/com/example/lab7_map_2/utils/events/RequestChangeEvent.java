package com.example.lab7_map_2.utils.events;

import com.example.lab7_map_2.Domain.FriendRequest;

public class RequestChangeEvent implements Event{
    private ChangeEventType type;
    private FriendRequest oldRequest;
    private FriendRequest newRequest;

    public RequestChangeEvent(ChangeEventType type, FriendRequest old_r, FriendRequest new_r) {
        this.type = type;
        this.oldRequest = old_r;
        this.newRequest = new_r;
    }


    public ChangeEventType getType() {
        return type;
    }
    public FriendRequest getOldFriendship() {
        return oldRequest;
    }
    public FriendRequest getNewFriendship() { return newRequest; }


    public void setType(ChangeEventType type) { this.type = type; }
    public void setOldUser(FriendRequest oldFriendship) { this.oldRequest = oldFriendship; }
    public void setNewUser(FriendRequest newFriendship) { this.newRequest = newFriendship;}
}
