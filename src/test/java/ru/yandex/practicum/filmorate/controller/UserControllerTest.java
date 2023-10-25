package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

public class UserControllerTest {
    @BeforeEach
    void setUp() {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
    }

    @Test()
    void emptyEmail() {
        User user = User.builder()
                .email("")
                .login("test")
                .name("test")
                .birthday(LocalDate.of(1900, 1, 1))
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if ((user.getEmail() == null || user.getEmail().isBlank())) {
                throw new ValidationException("Электронная почта не может быть пустой");
            }
        });

    }

    @Test()
    void emailWithoutDog() {
        User user = User.builder()
                .email("nikita.novokhnov.gmail.com")
                .login("test")
                .name("test")
                .birthday(LocalDate.of(1900, 1, 1))
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if (!user.getEmail().contains("@")) {
                throw new ValidationException("Электронная почта должна содержать символ @");
            }
        });

    }

    @Test()
    void emptyLogin() {
        User user = User.builder()
                .email("nikita.novokhnov.gmail.com")
                .login("")
                .name("test")
                .birthday(LocalDate.of(1900, 1, 1))
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if ((user.getLogin() == null || user.getLogin().isBlank())) {
                throw new ValidationException("Логин не может быть пустым");
            }
        });
    }

    @Test()
    void loginWithSpaces() {
        User user = User.builder()
                .email("nikita.novokhnov.gmail.com")
                .login("logi n")
                .name("test")
                .birthday(LocalDate.of(1900, 1, 1))
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if (user.getLogin().contains(" ")) {
                throw new ValidationException("Логин не может содержать пробелы");
            }
        });
    }

    @Test()
    void futureBirthDay() {
        User user = User.builder()
                .email("nikita.novokhnov.gmail.com")
                .login("logi n")
                .name("test")
                .birthday(LocalDate.of(3000, 1, 1))
                .build();

        Assertions.assertThrows(ValidationException.class, () -> {
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Дата рождения не может быть в будущем");
            }
        });
    }
}
