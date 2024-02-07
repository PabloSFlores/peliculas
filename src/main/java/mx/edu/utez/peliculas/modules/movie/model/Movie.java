package mx.edu.utez.peliculas.modules.movie.model;

import jakarta.persistence.*;
import lombok.*;
import mx.edu.utez.peliculas.modules.categoy.model.Category;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
