package me.denis.repository;

import me.denis.model.User;

import java.util.*;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public Collection<User> getUsers () {
        return Collections.unmodifiableCollection(users);
    }

    public Optional<User> findUserByLogin (String login) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findAny();
    }

    public Optional<User> findUserByLoginPassword (String login, String password) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findAny();
    }

    public void addUser (User user) {
        users.add(user);
    }

}
