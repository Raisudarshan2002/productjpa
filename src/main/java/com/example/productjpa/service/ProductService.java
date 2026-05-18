package com.example.productjpa.service;


import com.example.productjpa.entity.Product;
import com.example.productjpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void addProduct(Product product, MultipartFile imageFile) {

        if (!imageFile.isEmpty()) {

            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName =
                    UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

            File saveFile = new File(uploadDir + fileName);

            try {

                imageFile.transferTo(saveFile);

            } catch (IOException e) {

                throw new RuntimeException("Image Upload Failed", e);
            }

            product.setImageName(fileName);
        }

        productRepository.saveAndFlush(product);
    }

    public List<Product> findProduct(String name) {
        return productRepository.findAllByNameContaining(name);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + id));
    }
}
