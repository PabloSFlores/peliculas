package mx.edu.utez.peliculas.modules.movie.service;

import mx.edu.utez.peliculas.kernel.Errors;
import mx.edu.utez.peliculas.kernel.ResponseApi;
import mx.edu.utez.peliculas.modules.categoy.model.ICategoryRepository;
import mx.edu.utez.peliculas.modules.movie.controller.dto.SearchMovieDto;
import mx.edu.utez.peliculas.modules.movie.model.IMovieRepository;
import mx.edu.utez.peliculas.modules.movie.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class MovieService {
    private final IMovieRepository iMovieRepository;
    private final ICategoryRepository iCategoryRepository;

    public MovieService(IMovieRepository iMovieRepository, ICategoryRepository iCategoryRepository) {
        this.iMovieRepository = iMovieRepository;
        this.iCategoryRepository = iCategoryRepository;
    }

    @Transactional(readOnly = true)
    public ResponseApi<Page<Movie>> findAll(Pageable pageable, SearchMovieDto searchMovieDto) {
        Page<Movie> movies;
        if (searchMovieDto == null) {
            movies = this.iMovieRepository.findAll(pageable);
        } else if (searchMovieDto.getCategoryId() == null && (searchMovieDto.getFirstDate() == null || searchMovieDto.getSecondDate() == null)) {
            movies = this.iMovieRepository.findAllByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCase(
                    searchMovieDto.getTitle(),
                    searchMovieDto.getDirector(),
                    pageable);
        } else if (searchMovieDto.getFirstDate() == null || searchMovieDto.getSecondDate() == null) {
            movies = this.iMovieRepository.findAllByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCaseAndCategory_Id(
                    searchMovieDto.getTitle(),
                    searchMovieDto.getDirector(),
                    searchMovieDto.getCategoryId(),
                    pageable);
        } else if (searchMovieDto.getCategoryId() == null) {
            movies = this.iMovieRepository.findAllByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCaseAndPublishDateBetween(
                    searchMovieDto.getTitle(),
                    searchMovieDto.getDirector(),
                    searchMovieDto.getFirstDate(),
                    searchMovieDto.getSecondDate(),
                    pageable);
        } else {
            movies = this.iMovieRepository.findAllByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCaseAndCategory_IdAndPublishDateBetween(
                    searchMovieDto.getTitle(),
                    searchMovieDto.getDirector(),
                    searchMovieDto.getCategoryId(),
                    searchMovieDto.getFirstDate(),
                    searchMovieDto.getSecondDate(),
                    pageable);
        }
        return new ResponseApi<>(
                movies,
                HttpStatus.OK,
                false,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public ResponseApi<Movie> findOne(Long id) {
        Optional<Movie> optionalMovie = this.iMovieRepository.findById(id);
        return optionalMovie.map(movie -> new ResponseApi<>(
                movie,
                HttpStatus.OK,
                false,
                "Ok"
        )).orElseGet(() -> new ResponseApi<>(
                HttpStatus.NOT_FOUND,
                true,
                Errors.NO_DATA_FOUND.name()
        ));
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Movie> save(Movie movie) {
        if (this.iMovieRepository.existsByTitleContainingIgnoreCase(movie.getTitle())) {
            return new ResponseApi<>(
                    HttpStatus.BAD_REQUEST,
                    true,
                    Errors.DUPLICATED_RECORD.name()
            );
        }

        if (!this.iCategoryRepository.existsById(movie.getCategory().getId())) {
            return new ResponseApi<>(
                    HttpStatus.NOT_FOUND,
                    true,
                    Errors.NO_DATA_FOUND.name()
            );
        }

        return new ResponseApi<>(
                this.iMovieRepository.saveAndFlush(movie),
                HttpStatus.CREATED,
                false,
                "Movie created"
        );
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Movie> update(Movie movie) {
        if (!this.iMovieRepository.existsById(movie.getId())) {
            return new ResponseApi<>(
                    HttpStatus.BAD_REQUEST,
                    true,
                    Errors.NO_DATA_FOUND.name()
            );
        }
        if (this.iMovieRepository.existsByTitleContainingIgnoreCaseAndIdNot(
                movie.getTitle(), movie.getId())) {
            return new ResponseApi<>(
                    HttpStatus.BAD_REQUEST,
                    true,
                    Errors.DUPLICATED_RECORD.name()
            );
        }

        return new ResponseApi<>(
                this.iMovieRepository.saveAndFlush(movie),
                HttpStatus.OK,
                false,
                "Movie updated"
        );
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Movie> changeStatus(Long id) {
        Optional<Movie> optionalMovie = this.iMovieRepository.findById(id);
        if (optionalMovie.isEmpty()) {
            return new ResponseApi<>(
                    HttpStatus.BAD_REQUEST,
                    true,
                    Errors.NO_DATA_FOUND.name()
            );
        }

        Movie existingMovie = optionalMovie.get();
        existingMovie.setStatus(!existingMovie.getStatus());
        return new ResponseApi<>(
                this.iMovieRepository.saveAndFlush(existingMovie),
                HttpStatus.OK,
                false,
                "Movie status updated"
        );
    }
}