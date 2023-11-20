package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
public class FilmGenreDbStorage implements FilmGenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public void add(Long filmId, Long genreId) {
        String sqlQuery = "insert into film_genres(film_id, genre_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                filmId,
                genreId);
    }

    public void remove(Long filmId, Long genreId) {
        String sqlQuery = "delete from film_genres where film_id = ? and genre_id = ?";
        jdbcTemplate.update(sqlQuery,
                filmId,
                genreId);
    }

    public LinkedHashSet<Genre> getGenresByFilm(Long filmId) {
        String sqlQuery = "select * from film_genres fg left join genres g ON fg.genre_id = g.id where fg.film_id = ?";
        LinkedHashSet<Genre> genres = new LinkedHashSet<>(jdbcTemplate.query(sqlQuery, GenreDbStorage::createGenre, filmId));
        return genres;
    }
}
