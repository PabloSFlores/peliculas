package mx.edu.utez.peliculas.modules.categoy.service;

import mx.edu.utez.peliculas.modules.categoy.model.Category;
import mx.edu.utez.peliculas.modules.categoy.model.ICategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
public class CategoryService {
    private final ICategoryRepository iCategoryRepository;

    public CategoryService(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepository = iCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        return this.iCategoryRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Category save(Category category) {
        return this.iCategoryRepository.saveAndFlush(category);
    }
}
