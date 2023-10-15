package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long generatedId;

    @GetMapping()
    public Collection<User> getAll() {
        return users.values();
    }

    @PostMapping()
    public User create(@RequestBody User user) throws ValidationException {
        log.info("Создание пользователя: " + user);
        if ((user.getEmail() == null || user.getEmail().isBlank()) || !user.getEmail().contains("@")) {
            log.info("Электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        if ((user.getLogin() == null || user.getLogin().isBlank()) || user.getLogin().contains(" ")) {
            log.info("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        if ((user.getName() == null || user.getName().isBlank()) && !user.getLogin().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(++generatedId);
        users.put(user.getId(), user);

        log.info("Результат создания пользователя: " + user);
        return user;
    }

    @PutMapping()
    public User update(@RequestBody User user) throws ValidationException {
        log.info("Обновление пользователя: " + user);
        if (!users.containsKey(user.getId())) {
            log.info("Такого пользователя не существует");
            throw new ValidationException("Такого пользователя не существует");
        }

        if ((user.getEmail() == null || user.getEmail().isBlank()) || !user.getEmail().contains("@")) {
            log.info("Электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        if ((user.getLogin() == null || user.getLogin().isBlank()) || user.getLogin().contains(" ")) {
            log.info("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        if ((user.getName() == null || user.getName().isBlank()) && !user.getLogin().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);

        log.info("Результат обновления пользователя: " + user);
        return user;
    }
}
