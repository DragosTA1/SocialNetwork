package com.example.lab7_map_2.Validator;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}