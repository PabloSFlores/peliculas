package mx.edu.utez.peliculas.modules.movie.controller;

import lombok.AllArgsConstructor;
import mx.edu.utez.peliculas.modules.movie.model.Movie;
import mx.edu.utez.peliculas.modules.movie.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/movie")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/")
    public Page<Movie> findAll(Pageable pageable) {
        return this.movieService.findAll(pageable);
    }

    @PostMapping("/")
    public Movie save(@RequestBody Movie movie) {
        return this.movieService.save(movie);
    }
}
