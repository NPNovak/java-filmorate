package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Validation;
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

        Validation.userValidation(user);

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

        Validation.userValidation(user);

        users.put(user.getId(), user);

        log.info("Результат обновления пользователя: " + user);
        return user;
    }
}
