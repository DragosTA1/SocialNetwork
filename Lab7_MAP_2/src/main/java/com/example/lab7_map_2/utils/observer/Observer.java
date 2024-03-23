package com.example.lab7_map_2.utils.observer;


import com.example.lab7_map_2.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}