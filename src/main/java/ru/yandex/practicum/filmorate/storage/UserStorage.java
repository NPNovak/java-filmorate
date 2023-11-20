package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    public Collection<User> getAll();

    public User getUserById(Long userId) throws NotFoundException;

    public User create(User user);

    public User update(User user) throws ValidationException;
}
