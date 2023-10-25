package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

interface FilmStorage {
    public Map<Long, Film> getAll();

    public Film create(Film film);

    public Film update(Film film) throws ValidationException;
}
