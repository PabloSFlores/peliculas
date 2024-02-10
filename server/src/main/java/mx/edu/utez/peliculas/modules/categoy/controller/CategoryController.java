package mx.edu.utez.peliculas.modules.categoy.controller;

import lombok.AllArgsConstructor;
import mx.edu.utez.peliculas.modules.categoy.model.Category;
import mx.edu.utez.peliculas.modules.categoy.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/category")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public Page<Category> getAll(Pageable pageable) {
        return this.categoryService.findAll(pageable);
    }

    @PostMapping("/")
    public Category save(@RequestBody Category category) {
        return this.categoryService.save(category);
    }
}
