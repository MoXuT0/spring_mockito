package me.denis.service;

import me.denis.exception.UserNonUniqueException;
import me.denis.model.User;
import me.denis.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getAllLogins (List<User> userList) {
        return userList.stream()
                .map(user -> user.getLogin())
                .collect(Collectors.toList());
    }

    public void createUser (String login, String password) {
        User user = new User(login, password);
        if (login == null || login.isEmpty() || login.isBlank()) {
            throw new IllegalArgumentException("Неправильно задан логин");
        }
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Неправильно задан пароль");
        }
        boolean userExist = userRepository.getUsers().stream()
                .anyMatch(u -> u.equals(user));
        if (userExist) {
            throw new UserNonUniqueException("Такой пользователь уже существует");
        }
        checkLogin(login, password);
        userRepository.addUser(user);
    }

    private boolean checkLogin (String login, String password) {
        User user = new User(login, password);
        return userRepository.getUsers().stream()
                .anyMatch(e -> e.equals(user));
    }

}
