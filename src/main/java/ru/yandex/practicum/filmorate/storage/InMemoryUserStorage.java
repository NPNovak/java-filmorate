package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long generatedId;

    public Collection<User> getAll() {
        return (Collection<User>) users;
    }

    public User getUserById(Long userId) throws NotFoundException {
        return users.get(userId);
    }

    public User create(User user) {
        user.setId(++generatedId);
        users.put(user.getId(), user);

        return user;
    }

    public User update(User user) throws ValidationException {
        users.put(user.getId(), user);

        return user;
    }
}
