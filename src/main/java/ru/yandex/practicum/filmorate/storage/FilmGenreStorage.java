package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;

public interface FilmGenreStorage {
    void add(Long filmId, Long genreId);

    void remove(Long filmId, Long genreId);

    LinkedHashSet<Genre> getGenresByFilm(Long filmId);
}
