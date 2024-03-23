package com.example.lab7_map_2.Validator;
import com.example.lab7_map_2.Domain.User;

import java.util.function.Predicate;

public class UserValidator implements Validator<User> {
    public UserValidator() {}

    @Override
    public void validate(User entity) throws ValidationException {
        String err = "";
        Predicate<String> stringNevid = String::isEmpty;
        Predicate<String> stringValid = stringNevid.negate().and(s -> s.matches("^[a-zA-Z\\s]+$"));

        if (!stringValid.test(entity.getFirstName())) { err += "Invalid first name! \n";}
        if (!stringValid.test(entity.getLastName())) { err += "Invalid last name invalid!\n";}

        if (!err.isEmpty()) {
            throw new ValidationException(err);
        }
    }
}

