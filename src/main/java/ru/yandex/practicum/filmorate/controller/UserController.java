package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.utils.Validation;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable Long userId) throws NotFoundException {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addUserFriend(@PathVariable Long userId, @PathVariable Long friendId) throws ValidationException {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeUserFriend(@PathVariable Long userId, @PathVariable Long friendId) throws ValidationException {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getUserFriends(@PathVariable Long userId) throws NotFoundException {
        return userService.getUserFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getUserCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) throws ValidationException {
        return userService.getUserCommonFriends(userId, otherId);
    }

    @PostMapping()
    public User create(@RequestBody User user) throws ValidationException {
        log.info("Создание пользователя: " + user);
        Validation.userValidation(user);
        log.info("Результат создания пользователя: " + user);

        return userService.create(user);
    }

    @PutMapping()
    public User update(@RequestBody User user) throws NotFoundException, ValidationException {
        log.info("Обновление пользователя: " + user);
        Validation.userValidation(user);
        log.info("Результат обновления пользователя: " + user);

        return userService.update(user);
    }
}
