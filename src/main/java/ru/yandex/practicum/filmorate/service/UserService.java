package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserService {
    public Collection<User> getAll();

    public Map<Long, User> getUsers();

    public User getUserById(Long userId) throws NotFoundException;

    public User create(User user);

    public User update(User user) throws ValidationException;

    public User addFriend(Long userId, Long friendId) throws ValidationException;

    public void deleteFriend(Long userId, Long friendId) throws ValidationException;

    public List<User> getUserFriends(Long userId) throws NotFoundException;

    public List<User> getUserCommonFriends(Long userId, Long otherId) throws ValidationException;
}
