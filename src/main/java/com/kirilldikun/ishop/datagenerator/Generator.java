package com.kirilldikun.ishop.datagenerator;

import com.github.javafaker.Faker;
import com.kirilldikun.ishop.dto.ImageDTO;
import com.kirilldikun.ishop.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static List<ProductDTO> generateFakeProducts(int numProducts) {
        Faker faker = new Faker();

        List<ProductDTO> products = new ArrayList<>();

        for (int i = 0; i < numProducts; i++) {
            ProductDTO product = new ProductDTO();
            product.setId(null);
            product.setName(faker.commerce().productName());
            product.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000)));
            product.setDescription(faker.lorem().sentence());
            product.setImages(generateFakeImages(faker));
            product.setCategoryId(1L);

            products.add(product);
        }

        return products;
    }

    private static List<ImageDTO> generateFakeImages(Faker faker) {
        List<ImageDTO> images = new ArrayList<>();

        ImageDTO image1 = new ImageDTO();
        image1.setUrl("https://source.unsplash.com/random/200x300");
        images.add(image1);

        ImageDTO image2 = new ImageDTO();
        image2.setUrl("https://source.unsplash.com/random/200x300");
        images.add(image2);

        return images;
    }
}
