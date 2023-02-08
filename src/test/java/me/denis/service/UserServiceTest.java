package me.denis.service;

import me.denis.exception.UserNonUniqueException;
import me.denis.model.User;
import me.denis.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.*;
import static me.denis.constant.UserConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Тест на пустой список логинов")
    public void shouldReturnEmptyListOfLogins() {
        when(userRepositoryMock.getUsers())
                .thenReturn(emptyList());
        assertTrue(userService.getAllLogins().isEmpty());
    }

    @Test
    @DisplayName("Тест на заполенный список логинов")
    public void shouldReturnCorrectListOfLogins() {
        when(userRepositoryMock.getUsers())
                .thenReturn(List.of(new User(CORRECT_LOGIN, CORRECT_PASSWORD)));
        assertThat(userService.getAllLogins()).isEqualTo(List.of(CORRECT_LOGIN));
    }

    @Test
    @DisplayName("Тест на создание пользователя с пустым или null логином")
    public void shouldThrowExceptionsWhenLoginIncorrect() {
        assertThatThrownBy(() -> userService.createUser(BLANK_LOGIN, CORRECT_PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Неправильно задан логин");
        assertThatThrownBy(() -> userService.createUser(null, CORRECT_PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Неправильно задан логин");
        verify(userRepositoryMock, never()).addUser(any(User.class));
    }

    @Test
    @DisplayName("Тест на создание пользователя с пустым или null паролем")
    public void shouldThrowExceptionsWhenPasswordIncorrect() {
        assertThatThrownBy(() -> userService.createUser(CORRECT_LOGIN, BLANK_PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Неправильно задан пароль");
        assertThatThrownBy(() -> userService.createUser(CORRECT_LOGIN, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Неправильно задан пароль");
        verify(userRepositoryMock, never()).addUser(any(User.class));
    }

    @Test
    @DisplayName("Тест на создание уже существующего пользователя")
    public void shouldThrowExceptionWhenUserAlreadyExists() {
        when(userRepositoryMock.getUsers()).thenReturn(List.of(new User(CORRECT_LOGIN, CORRECT_PASSWORD)));
        assertThatThrownBy(() -> userService.createUser(CORRECT_LOGIN, CORRECT_PASSWORD))
                .isInstanceOf(UserNonUniqueException.class).hasMessage("Такой пользователь уже существует");
        verify(userRepositoryMock, never()).addUser(any(User.class));
    }

    @Test
    @DisplayName("Тест на создание нового пользователя и вызов метода UserRepository")
    void shouldCallRepositoryMethodWhenAddIsCalled() {
        when(userRepositoryMock.getUsers()).thenReturn(emptyList());
        userService.createUser(CORRECT_LOGIN, CORRECT_PASSWORD);
        verify(userRepositoryMock, times(1)).addUser(new User(CORRECT_LOGIN, CORRECT_PASSWORD));
    }

}
