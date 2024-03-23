package com.example.lab7_map_2.Domain;
import java.util.Objects;


/**
 * User is an entity with log type id
 */
public class User extends Entity<Long> {
    //protected Long id
    private String firstName;
    private String lastName;
    private String password;


    public User(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User {" +
                "ID = " + id + ", "+
                "firstName = '" + firstName + "', " +
                "lastName = '" + lastName + "'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        //two users are equals if all their atributes are equals
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}