package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("userDbStorage")
public class UserServiceImpl implements UserService {
    private final UserDbStorage userDbStorage;
    private final FriendDbStorage friendDbStorage;

    public Collection<User> getAll() {
        return userDbStorage.getAll();
    }

    public User getUserById(Long userId) throws NotFoundException {
        if (userDbStorage.getUserById(userId) == null) {
            log.info("Такого" + userId + "не существует");
            throw new NotFoundException("Такого " + userId + " не существует");
        }
        return userDbStorage.getUserById(userId);
    }

    public User create(User user) {
        return userDbStorage.create(user);
    }

    public User update(User user) {
        if (userDbStorage.getUserById(user.getId()) == null) {
            log.info("Такого " + user.getId() + " не существует");
            throw new NotFoundException("Такого " + user.getId() + " не существует");
        }
        return userDbStorage.update(user);
    }

    public void addFriend(Long userId, Long friendId) throws ValidationException {
        if (userDbStorage.getUserById(userId) == null) {
            log.info("Такого " + userId + " не существует");
            throw new NotFoundException("Такого " + userId + " не существует");
        }

        if (userDbStorage.getUserById(friendId) == null) {
            throw new NotFoundException("Такого " + friendId + " нет");
        }
        friendDbStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) throws ValidationException {
        if (userDbStorage.getUserById(userId) == null) {
            log.info("Такого " + userId + " не существует");
            throw new ValidationException("Такого " + userId + " не существует");
        }

        if (userDbStorage.getUserById(friendId) == null) {
            throw new ValidationException("Такого " + friendId + " нет");
        }
        friendDbStorage.removeFriend(userId, friendId);
    }

    public List<User> getUserFriends(Long userId) throws NotFoundException {
        if (userDbStorage.getUserById(userId) == null) {
            log.info("Такого " + userId + " не существует");
            throw new NotFoundException("Такого " + userId + " не существует");
        }
        return friendDbStorage.getFriends(userId);
    }

    public List<User> getUserCommonFriends(Long userId, Long otherId) throws ValidationException {
        if (userDbStorage.getUserById(userId) == null) {
            log.info("Такого " + userId + " не существует");
            throw new ValidationException("Такого " + userId + " не существует");
        }

        if (userDbStorage.getUserById(otherId) == null) {
            log.info("Такого " + otherId + " не существует");
            throw new ValidationException("Такого " + otherId + " не существует");
        }
        return friendDbStorage.getCommonFriends(userId, otherId);
    }
}
