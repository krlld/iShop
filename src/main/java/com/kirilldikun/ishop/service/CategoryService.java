package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.entity.Category;
import com.kirilldikun.ishop.exception.CategoryAlreadyExistException;
import com.kirilldikun.ishop.exception.CategoryNotFoundException;
import com.kirilldikun.ishop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) throws IllegalArgumentException {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistException();
        }
        categoryRepository.save(category);
    }

    public void update(Long id, Category category) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        category.setId(id);
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(id);
    }
}
