package com.example.hdrezka.service;

import com.example.hdrezka.entity.Film;
import com.example.hdrezka.exception.FilmNotFoundException;
import com.example.hdrezka.repository.FilmRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class FilmService {

    // Declare the repository as final to ensure its immutability
    private final FilmRepository filmRepository;

    // Use constructor-based dependency injection
    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmById(Long id) throws FilmNotFoundException {
        return filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException("Фильм не найден!"));
    }

    public void saveFilm(Film film) {
        filmRepository.save(film);
    }

    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }
}