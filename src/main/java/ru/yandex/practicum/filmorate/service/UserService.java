package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.IUserService;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final UserStorage inMemoryUserStorage;

    public Collection<User> getAll() {
        return inMemoryUserStorage.getAll().values();
    }

    public Map<Long, User> getUsers() {
        return inMemoryUserStorage.getAll();
    }

    public User getUserById(Long userId) throws NotFoundException {
        if (!inMemoryUserStorage.getAll().containsKey(userId)) {
            log.info("Такого" + userId + "не существует");
            throw new NotFoundException("Такого " + userId + " не существует");
        }
        return inMemoryUserStorage.getUserById(userId);
    }

    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    public User update(User user) throws ValidationException {
        if (!inMemoryUserStorage.getAll().containsKey(user.getId())) {
            log.info("Такого " + user.getId() + " не существует");
            throw new ValidationException("Такого " + user.getId() + " не существует");
        }
        return inMemoryUserStorage.update(user);
    }

    public User addFriend(Long userId, Long friendId) throws ValidationException {
        if (!inMemoryUserStorage.getAll().containsKey(userId)) {
            log.info("Такого " + userId + " не существует");
            throw new NotFoundException("Такого " + userId + " не существует");
        }

        if (!inMemoryUserStorage.getAll().containsKey(friendId)) {
            throw new NotFoundException("Такого " + friendId + " нет");
        }
        return inMemoryUserStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) throws ValidationException {
        if (!inMemoryUserStorage.getAll().containsKey(userId)) {
            log.info("Такого " + userId + " не существует");
            throw new ValidationException("Такого " + userId + " не существует");
        }

        if (!inMemoryUserStorage.getAll().containsKey(friendId)) {
            throw new ValidationException("Такого " + friendId + " нет");
        }
        inMemoryUserStorage.deleteFriend(userId, friendId);
    }

    public List<User> getUserFriends(Long userId) throws NotFoundException {
        if (!inMemoryUserStorage.getAll().containsKey(userId)) {
            log.info("Такого " + userId + " не существует");
            throw new NotFoundException("Такого " + userId + " не существует");
        }
        return inMemoryUserStorage.getUserFriends(userId);
    }

    public List<User> getUserCommonFriends(Long userId, Long otherId) throws ValidationException {
        if (!inMemoryUserStorage.getAll().containsKey(userId)) {
            log.info("Такого " + userId + " не существует");
            throw new ValidationException("Такого " + userId + " не существует");
        }

        if (!inMemoryUserStorage.getAll().containsKey(otherId)) {
            log.info("Такого " + otherId + " не существует");
            throw new ValidationException("Такого " + otherId + " не существует");
        }
        return inMemoryUserStorage.getUserCommonFriends(userId, otherId);
    }
}
