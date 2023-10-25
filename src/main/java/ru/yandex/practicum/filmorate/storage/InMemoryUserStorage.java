package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long generatedId;

    public Map<Long, User> getAll() {
        return users;
    }

    public User getUserById(Long userId) throws NotFoundException {
        return users.get(userId);
    }

    public List<User> getUserFriends(Long userId) throws NotFoundException {
        return users.get(userId).getFriends().stream()
                .map(id -> users.get(id))
                .collect(Collectors.toList());
    }

    public List<User> getUserCommonFriends(Long userId, Long otherId) throws ValidationException {
        Set<Long> commonFriends = new HashSet<>(users.get(userId).getFriends());
        commonFriends.retainAll(users.get(otherId).getFriends());

        return commonFriends.stream()
                .map(id -> users.get(id))
                .collect(Collectors.toList());
    }

    public User addFriend(Long userId, Long friendId) throws ValidationException {
        users.get(userId).getFriends().add(friendId);
        users.get(friendId).getFriends().add(userId);

        return users.get(userId);
    }

    public void deleteFriend(Long userId, Long friendId) throws ValidationException {
        users.get(userId).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(userId);
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
