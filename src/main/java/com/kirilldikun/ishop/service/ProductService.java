package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.ProductDTO;
import com.kirilldikun.ishop.entity.Product;
import com.kirilldikun.ishop.exception.CategoryNotFoundException;
import com.kirilldikun.ishop.exception.ProductAlreadyExistsException;
import com.kirilldikun.ishop.exception.ProductNotFoundException;
import com.kirilldikun.ishop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final ImageService imageService;

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(this::mapToProductDTO).toList();
    }

    public void save(ProductDTO productDTO) throws CategoryNotFoundException, ProductAlreadyExistsException {
        if (!categoryService.existsById(productDTO.getCategoryId())) {
            throw new CategoryNotFoundException();
        }
        if (productRepository.existsByName(productDTO.getName())) {
            throw new ProductAlreadyExistsException();
        }
        Product product = mapToProduct(productDTO);
        productRepository.save(product);
        imageService.saveAll(productDTO.getImages(), product.getId());
    }

    public void update(Long id, ProductDTO productDTO)
            throws ProductNotFoundException, CategoryNotFoundException, ProductAlreadyExistsException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        if (!categoryService.existsById(productDTO.getCategoryId())) {
            throw new CategoryNotFoundException();
        }
        if (productRepository.existsByName(productDTO.getName()) &&
                !productRepository.findByName(productDTO.getName()).orElseThrow(ProductNotFoundException::new)
                        .getId().equals(id)) {
            throw new ProductAlreadyExistsException();
        }
        Product product = mapToProduct(productDTO);
        product.setId(id);
        productRepository.save(product);
        imageService.updateProductImages(productDTO.getImages(), product.getId());
    }

    public void delete(Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public Product findById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setImages(product.getImages().stream().map(imageService::mapToImageDTO).toList());
        return productDTO;
    }

    public Product mapToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryService.findById(productDTO.getCategoryId()));
        return product;
    }
}
