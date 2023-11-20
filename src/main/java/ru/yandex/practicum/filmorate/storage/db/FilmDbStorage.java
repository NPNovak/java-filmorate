package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
//implements FilmStorage
public class FilmDbStorage {
    private final JdbcTemplate jdbcTemplate;

    //@Override
    public Collection<Film> getAll() {
        String sqlQuery = "select * from films";
        return jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        String sqlQuery = "select * from films f order by f.id in (select count(user_id) from likes l group by l.film_id order by count(user_id) desc) limit ?;";
        return jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, count);
    }

    //@Override
    public Film getFilmById(Long id) {
        String sqlQuery = "select * from films f left join mpa m on m.id = f.mpa_id where f.id = ?";
        List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, id);
        if (films.size() != 1) {
            throw new NotFoundException(String.format("film with id %s not found", id));
        }
        return films.get(0);
    }

    // @Override
    public Film create(Film film) {
        String sqlQuery = "insert into films(name, release_date, description, duration, mpa_id) values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setObject(2, film.getReleaseDate());
            stmt.setString(3, film.getDescription());
            stmt.setInt(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        return getFilmById(keyHolder.getKey().longValue());
    }

    public Film update(Film film) {
        String sqlQuery = "update films set name = ?, release_date = ?, description = ?, duration = ?, mpa_id = ? where id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getReleaseDate(), film.getDescription(), film.getDuration(), film.getMpa().getId(), film.getId());
        return getFilmById(film.getId());
    }

    static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .description(rs.getString("description"))
                .duration(rs.getInt("duration"))
                .mpa(Mpa.builder()
                        .id(rs.getLong("mpa_id"))
                        .build())
                .build();
    }
}
