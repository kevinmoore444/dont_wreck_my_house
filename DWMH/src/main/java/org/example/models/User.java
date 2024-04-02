package org.example.models;

import java.util.Objects;

public class User {
    //Attributes
    private int userId;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;

    //Constructors
    public User(){}

    public User(int userId) {
        this.userId = userId;
    }

    public User(int userId, String first_name, String last_name, String email, String phone) {
        this.userId = userId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
    }

    //Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Override the equals and hashcode methods.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (!Objects.equals(first_name, user.first_name)) return false;
        if (!Objects.equals(last_name, user.last_name)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(phone, user.phone)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
        result = 31 * result + (last_name != null ? last_name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

}
