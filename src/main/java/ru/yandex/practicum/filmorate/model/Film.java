package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Film {
    private Long id;
    private String name;
    private String description;
    private LinkedHashSet<Genre> genres = new LinkedHashSet<>();
    private Mpa mpa;
    private Integer rate;
    private LocalDate releaseDate;
    private Integer duration;
    private Integer likes;
}
