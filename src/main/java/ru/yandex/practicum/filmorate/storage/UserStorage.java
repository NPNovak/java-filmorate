package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {
    public Map<Long, User> getAll();

    public User getUserById(Long userId) throws NotFoundException;

    public List<User> getUserFriends(Long userId) throws NotFoundException;

    public List<User> getUserCommonFriends(Long userId, Long otherId) throws ValidationException;

    public User addFriend(Long userId, Long friendId) throws ValidationException;

    public void deleteFriend(Long userId, Long friendId) throws ValidationException;

    public User create(User user);

    public User update(User user) throws ValidationException;
}
