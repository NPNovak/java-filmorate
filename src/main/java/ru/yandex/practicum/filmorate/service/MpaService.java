package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaService {
    public Collection<Mpa> getAll();

    public Mpa getById(Long id);

}
