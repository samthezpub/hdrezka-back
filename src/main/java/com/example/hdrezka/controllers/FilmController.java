package com.example.hdrezka.controllers;

import com.example.hdrezka.entity.Film;
import com.example.hdrezka.exception.FilmNotFoundException;
import com.example.hdrezka.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
public class FilmController {

    // Declare the service as final to ensure its immutability
    private final FilmService filmService;
    private final HandlerMapping resourceHandlerMapping;

    // Use constructor-based dependency injection
    @Autowired
    public FilmController(FilmService filmService, @Qualifier("resourceHandlerMapping") HandlerMapping resourceHandlerMapping) {
        this.filmService = filmService;
        this.resourceHandlerMapping = resourceHandlerMapping;
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createFilm(@RequestBody Film film) {
        filmService.saveFilm(film);
        return ResponseEntity.ok("Фильм создан");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}