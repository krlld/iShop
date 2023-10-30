package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.CategoryDTO;
import com.kirilldikun.ishop.entity.Category;
import com.kirilldikun.ishop.exception.CategoryAlreadyExistException;
import com.kirilldikun.ishop.exception.CategoryNotFoundException;
import com.kirilldikun.ishop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(this::mapToCategoryDTO).toList();
    }

    public void save(CategoryDTO categoryDTO) throws CategoryAlreadyExistException {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new CategoryAlreadyExistException();
        }
        Category category = mapToCategory(categoryDTO);
        categoryRepository.save(category);
    }

    public void update(Long id, CategoryDTO categoryDTO)
            throws CategoryNotFoundException, CategoryAlreadyExistException {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        if (categoryRepository.existsByName(categoryDTO.getName()) &&
                !categoryRepository.findByName(categoryDTO.getName()).orElseThrow(CategoryNotFoundException::new)
                        .getId().equals(id)) {
            throw new CategoryAlreadyExistException();
        }
        Category category = mapToCategory(categoryDTO);
        category.setId(id);
        categoryRepository.save(category);
    }

    public void delete(Long id) throws CategoryNotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    public Category findById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }

    public CategoryDTO mapToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public Category mapToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}
