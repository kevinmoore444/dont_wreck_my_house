package org.example.data;

import org.example.models.User;

public interface UserRepository {
    User findUserByID(int userId);
}
