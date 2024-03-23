package com.example.lab7_map_2.Controller;

import com.example.lab7_map_2.Service.FriendshipService;
import com.example.lab7_map_2.Service.UserService;

public class UserController {
    private UserService userService;
    private FriendshipService friendshipService;

    public void setService(UserService userSev, FriendshipService friendshipSev) {
        userService = userSev;
        friendshipService = friendshipSev;
    }
}
