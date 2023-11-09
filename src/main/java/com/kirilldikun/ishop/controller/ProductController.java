package com.kirilldikun.ishop.controller;

import com.kirilldikun.ishop.datagenerator.Generator;
import com.kirilldikun.ishop.dto.ProductDTO;
import com.kirilldikun.ishop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
//        List<ProductDTO> productDTOList1 = Generator.generateFakeProducts(50);
//        for (ProductDTO productDTO : productDTOList1) {
//            System.out.println(productDTO);
//            productService.save(productDTO);
//        }
        List<ProductDTO> productDTOList = productService.findAll();
        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductDTO productDTO) {
        productService.save(productDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        productService.update(id, productDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
