package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.*;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmDbStorage filmDbStorage;
    private final FilmGenreDbStorage filmGenreDbStorage;
    private final UserDbStorage userDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private final LikeDbStorage likeDbStorage;

    public Collection<Film> getAll() {
        Collection<Film> films = filmDbStorage.getAll();
        films.forEach(film -> {
            film.setMpa(mpaDbStorage.getById(film.getMpa().getId()));
            film.setGenres(filmGenreDbStorage.getGenresByFilm(film.getId()));
        });

        return films;
    }

    public Collection<Film> getPopularFilms(Integer count) {
        Collection<Film> films = filmDbStorage.getPopularFilms(count);
        films.forEach(film -> {
            film.setMpa(mpaDbStorage.getById(film.getMpa().getId()));
            film.setGenres(filmGenreDbStorage.getGenresByFilm(film.getId()));
        });

        return films;
    }

    public Film getFilmById(Long filmId) {

        if (filmDbStorage.getFilmById(filmId) == null) {
            log.info("Такого " + filmId + " не существует");
            throw new NotFoundException("Такого " + filmId + " не существует");
        }
        Film film = filmDbStorage.getFilmById(filmId);
        film.setMpa(mpaDbStorage.getById(film.getMpa().getId()));
        film.setGenres(filmGenreDbStorage.getGenresByFilm(film.getId()));

        return film;
    }

    public Film create(Film film) {
        Film newFilm = filmDbStorage.create(film);
        film.getGenres().forEach(genre -> {
            filmGenreDbStorage.add(newFilm.getId(), genre.getId());
        });
        newFilm.setMpa(mpaDbStorage.getById(newFilm.getMpa().getId()));
        newFilm.setGenres(filmGenreDbStorage.getGenresByFilm(newFilm.getId()));
        newFilm.setLikes(likeDbStorage.getLikesByFilm(newFilm.getId()));

        return newFilm;
    }

    public Film update(@RequestBody Film film) throws ValidationException {
        Film currentFilm = filmDbStorage.getFilmById(film.getId());
        if (currentFilm == null) {
            log.info("Такого " + film.getId() + " не существует");
            throw new ValidationException("Такого " + film.getId() + " не существует");
        }
        currentFilm.setGenres(filmGenreDbStorage.getGenresByFilm(currentFilm.getId()));

        Film updatedFilm = filmDbStorage.update(film);

        if (currentFilm.getGenres() != null && currentFilm.getGenres().size() > 0) {
            currentFilm.getGenres().forEach(genre -> {
                filmGenreDbStorage.remove(updatedFilm.getId(), genre.getId());
            });
        }

        if (film.getGenres() != null && film.getGenres().size() > 0) {
            film.getGenres().forEach(genre -> {
                filmGenreDbStorage.add(updatedFilm.getId(), genre.getId());
            });
        }

        updatedFilm.setMpa(mpaDbStorage.getById(updatedFilm.getMpa().getId()));
        updatedFilm.setGenres(filmGenreDbStorage.getGenresByFilm(updatedFilm.getId()));
        updatedFilm.setLikes(likeDbStorage.getLikesByFilm(updatedFilm.getId()));

        return updatedFilm;
    }

    public void likeFilmById(Long filmId, Long userId) throws ValidationException {
        if (filmDbStorage.getFilmById(filmId) == null) {
            throw new ValidationException("Такого " + filmId + " не существует");
        }

        if (userDbStorage.getUserById(userId) == null) {
            throw new ValidationException("Такого " + userId + " не существует");
        }

        likeDbStorage.add(filmId, userId);
    }

    public void deleteLikeFromFilmById(Long filmId, Long userId) {
        if (filmDbStorage.getFilmById(filmId) == null) {
            throw new NotFoundException("Такого " + filmId + " не существует");
        }

        if (userDbStorage.getUserById(userId) == null) {
            throw new NotFoundException("Такого " + userId + " не существует");
        }

        likeDbStorage.remove(filmId, userId);
    }
}
