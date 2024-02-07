package mx.edu.utez.peliculas.modules.movie.service;

import mx.edu.utez.peliculas.modules.movie.model.IMovieRepository;
import mx.edu.utez.peliculas.modules.movie.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
public class MovieService {
    private final IMovieRepository iMovieRepository;

    public MovieService(IMovieRepository iMovieRepository) {
        this.iMovieRepository = iMovieRepository;
    }

    @Transactional(readOnly = true)
    public Page<Movie> findAll(Pageable pageable) {
        return this.iMovieRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Movie save(Movie movie) {
        return this.iMovieRepository.saveAndFlush(movie);
    }
}
