package com.example.lab7_map_2.Validator;

import com.example.lab7_map_2.Domain.Message;

import java.util.function.Predicate;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException {
        String err = "";
        if (entity.getMessage().isEmpty()) { err += "You can not send a empty message! \n";}
        if (entity.getTo().isEmpty()) { err += "Please select one or more people to send a message to! ";}

        if (!err.isEmpty()) {
            throw new ValidationException(err);
        }
    }
}
