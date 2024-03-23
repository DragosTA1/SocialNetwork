package com.example.lab7_map_2.utils.observer;


import com.example.lab7_map_2.utils.events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}
