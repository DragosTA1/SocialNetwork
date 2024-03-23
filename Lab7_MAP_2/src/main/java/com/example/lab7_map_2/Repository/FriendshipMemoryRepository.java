package com.example.lab7_map_2.Repository;

import com.example.lab7_map_2.Domain.Friendship;
import com.example.lab7_map_2.Domain.Tuple;

import java.util.ArrayList;
import java.util.Objects;

public class FriendshipMemoryRepository extends MemoryRepository<Tuple<Long, Long>, Friendship> implements FriendshipRepository{

    public Iterable<Long> findFriends(Long id) {
        ArrayList<Long> friends =  new ArrayList<>();
        for (Friendship friendship : entities.values()) {
            if(Objects.equals(friendship.getId().getLeft(), id)) {
                friends.add(friendship.getId().getRight());
            }
            if(Objects.equals(friendship.getId().getRight(), id)) {
                friends.add(friendship.getId().getLeft());
            }
        }
        return friends;
    }
}
