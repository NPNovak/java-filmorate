package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Long userId, Long friendId) {
        String sqlQuery = "insert into friends (user_id, friend_id) values(?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        String sqlQuery = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getFriends(Long userId) {
        String sqlQuery = "select * from users u join friends f ON u.id = f.friend_id where f.user_id = ?";
        List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId);
        return users;
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long friendId) {
        String sqlQuery = "select * from users where id in (select friend_id from friends where user_id = ? intersect select friend_id from friends where user_id = ?);";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId, friendId);
    }
}
