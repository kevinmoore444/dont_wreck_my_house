package org.example.data;

import org.example.models.User;

import static org.example.TestHelper.makeUser;

public class UserRepositoryDouble implements UserRepository {
    @Override
    public User findUserByID(int userId) {
        return userId== 5 ? null: makeUser(1);
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}
