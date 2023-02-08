package me.denis.repository;

import me.denis.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static me.denis.constant.UserConstants.*;

public class UserRepositoryTest {

    private UserRepository userRepository;
    private final User testUser = new User(CORRECT_LOGIN, CORRECT_PASSWORD);

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
        userRepository.addUser(testUser);
    }

    @Test
    @DisplayName("Тест на пустой список пользователей")
    public void isUserListEmpty() {
        UserRepository empty = new UserRepository();
        Assertions.assertTrue(empty.getUsers().isEmpty());
    }

    @Test
    @DisplayName("Тест на заполенный список пользователей")
    public void isUserListFilled() {
        Assertions.assertTrue(userRepository.getUsers().contains(testUser));
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину если такой пользователь существует")
    public void findByLoginIfUserExists() {
        Assertions.assertEquals(userRepository.findUserByLogin(CORRECT_LOGIN),
                userRepository.getUsers().stream().findAny());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину если такого пользователь нет")
    public void findByLoginIfUserDoesNotExists() {
        Assertions.assertNotEquals(userRepository.findUserByLogin(WRONG_LOGIN),
                userRepository.getUsers().stream().findAny());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину и паролю если такой пользователь существует")
    public void findByLoginPasswordIfUserExists() {
        Assertions.assertEquals(userRepository.findUserByLoginPassword(CORRECT_LOGIN, CORRECT_PASSWORD),
                userRepository.getUsers().stream().findAny());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину и паролю если логин неправильный")
    public void findByLoginPasswordIfWrongLogin() {
        Assertions.assertNotEquals(userRepository.findUserByLoginPassword(WRONG_LOGIN, CORRECT_PASSWORD),
                userRepository.getUsers().stream().findAny());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину и паролю если пароль неправильный")
    public void findByLoginPasswordIfWrongPassword() {
        Assertions.assertNotEquals(userRepository.findUserByLoginPassword(CORRECT_LOGIN, WRONG_PASSWORD),
                userRepository.getUsers().stream().findAny());
    }

}
