package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.ImageDTO;
import com.kirilldikun.ishop.entity.Image;
import com.kirilldikun.ishop.exception.ProductNotFoundException;
import com.kirilldikun.ishop.repository.ImageRepository;
import com.kirilldikun.ishop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final ProductRepository productRepository;

    public void saveAll(List<ImageDTO> imageDTOS, Long productId) {
        imageDTOS.forEach(imageDTO -> imageDTO.setProductId(productId));
        List<Image> images = imageDTOS.stream().map(this::mapToImage).toList();
        imageRepository.saveAll(images);
    }

    public void updateProductImages(List<ImageDTO> imageDTOS, Long productId) {
        imageRepository.deleteAll(
                imageRepository.findByProduct(productRepository.findById(productId).orElseThrow(ProductNotFoundException::new))
        );
        saveAll(imageDTOS, productId);
    }

    public ImageDTO mapToImageDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setUrl(image.getUrl());
        imageDTO.setProductId(image.getProduct().getId());
        return imageDTO;
    }

    public Image mapToImage(ImageDTO imageDTO) throws ProductNotFoundException {
        Image image = new Image();
        image.setId(imageDTO.getId());
        image.setUrl(imageDTO.getUrl());
        image.setProduct(productRepository.findById(imageDTO.getProductId()).orElseThrow(ProductNotFoundException::new));
        return image;
    }
}
