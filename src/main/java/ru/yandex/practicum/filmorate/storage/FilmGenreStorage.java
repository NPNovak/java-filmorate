package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;

public interface FilmGenreStorage {
    public void add(Long filmId, Long genreId);
    public void remove(Long filmId, Long genreId);
    public LinkedHashSet<Genre> getGenresByFilm(Long filmId);
}
