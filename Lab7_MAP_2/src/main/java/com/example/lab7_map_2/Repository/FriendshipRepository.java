package com.example.lab7_map_2.Repository;

import com.example.lab7_map_2.Domain.Friendship;
import com.example.lab7_map_2.Domain.Tuple;


public interface FriendshipRepository extends Repository<Tuple<Long, Long>, Friendship> {
    Iterable<Long> findFriends(Long id);
}
