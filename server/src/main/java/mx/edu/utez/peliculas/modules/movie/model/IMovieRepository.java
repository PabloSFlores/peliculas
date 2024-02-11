package mx.edu.utez.peliculas.modules.movie.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {

//    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE %:title% AND LOWER(m.director) LIKE %:director% AND m.category.id = :categoryId AND m.publishDate BETWEEN :startDate AND :endDate")
//    List<Movie> findByTitleDirectorCategoryAndPublishDate(@Param("title") String title, @Param("director") String director, @Param("categoryId") Long categoryId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Page<Movie> findAllByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCaseAndCategory_IdAndPublishDateBetween(
            String title, String director, Long category_id, LocalDate firstDate, LocalDate secondDate, Pageable pageable
    );

    Page<Movie> findAllByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCaseAndPublishDateBetween(
            String title, String director, LocalDate firstDate, LocalDate secondDate, Pageable pageable
    );

    Page<Movie> findAll(Pageable pageable);

    Boolean existsByTitleContainingIgnoreCase(String title);

    Boolean existsByTitleContainingIgnoreCaseAndIdNot(String title, Long id);

    List<Movie> findAllByCategory_Id(Long categoryId);

}
