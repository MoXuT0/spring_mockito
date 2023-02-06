package me.denis.repository;

import me.denis.constant.UserConstants;
import me.denis.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {

    private UserRepository userRepository;
    private final User testUser = new User(UserConstants.CORRECT_LOGIN, UserConstants.CORRECT_PASSWORD);

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
        Assertions.assertEquals(userRepository.getUsers().toString(), userRepository.getUsers().toString());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину если такой пользователь существует")
    public void findByLoginIfUserExists() {
        Assertions.assertEquals(userRepository.findUserByLogin(UserConstants.CORRECT_LOGIN),
                userRepository.getUsers().stream().findAny());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину если такого пользователь нет")
    public void findByLoginIfUserDoesNotExists() {
        Assertions.assertNotEquals(userRepository.findUserByLogin(UserConstants.WRONG_LOGIN),
                userRepository.getUsers().stream().findAny());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину и паролю если такой пользователь существует")
    public void findByLoginPasswordIfUserExists() {
        Assertions.assertEquals(userRepository.findUserByLoginPassword(UserConstants.CORRECT_LOGIN, UserConstants.CORRECT_PASSWORD),
                userRepository.getUsers().stream().findAny());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину и паролю если логин неправильный")
    public void findByLoginPasswordIfWrongLogin() {
        Assertions.assertNotEquals(userRepository.findUserByLoginPassword(UserConstants.WRONG_LOGIN, UserConstants.CORRECT_PASSWORD),
                userRepository.getUsers().stream().findAny());
    }

    @Test
    @DisplayName("Тест на поиск пользователя по логину и паролю если пароль неправильный")
    public void findByLoginPasswordIfWrongPassword() {
        Assertions.assertNotEquals(userRepository.findUserByLoginPassword(UserConstants.CORRECT_LOGIN, UserConstants.WRONG_PASSWORD),
                userRepository.getUsers().stream().findAny());
    }

}
