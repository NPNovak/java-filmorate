package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    public void add(Long userId, Long filmId) {
        String sqlQuery = "insert into likes(user_id, film_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                userId,
                filmId);
    }

    public void remove(Long userId, Long filmId) {
        String sqlQuery = "delete from likes where user_id = ? and film_id = ?";
        jdbcTemplate.update(sqlQuery,
                userId,
                filmId);
    }

    public Integer getLikesByFilm(Long filmId) {
        String sqlQuery = "select count(user_id) from likes where film_id = ?";
        List<Integer> likesList = jdbcTemplate.queryForList(sqlQuery, Integer.class, filmId);
        return likesList.get(0);
    }
}
