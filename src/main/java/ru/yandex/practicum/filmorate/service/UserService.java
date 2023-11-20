package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getAll();

    User getUserById(Long userId) throws NotFoundException;

    User create(User user);

    User update(User user) throws NotFoundException;

    void addFriend(Long userId, Long friendId) throws ValidationException;

    void deleteFriend(Long userId, Long friendId) throws ValidationException;

    List<User> getUserFriends(Long userId) throws NotFoundException;

    List<User> getUserCommonFriends(Long userId, Long otherId) throws ValidationException;
}
