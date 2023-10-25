package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

interface UserStorage {
    public Map<Long, User> getAll();

    public User create(User user);

    public User update(User user) throws ValidationException;
}
