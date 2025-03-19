package com.example.jooqtest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jooqtest.model.ProductDto;
import com.example.jooqtest.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductDto> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Transactional
    public ProductDto save(ProductDto productDto) {
        productDto.setVersion(0);
        return productRepository.save(productDto);
    }

    @Transactional
    public ProductDto update(ProductDto productDto) {
        ProductDto existing = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        productDto.setVersion(existing.getVersion() + 1);
        return productRepository.update(productDto);
    }

    @Transactional
    public void delete(Integer id) {
        productRepository.delete(id);
    }
    
    @Transactional
    public ProductDto reduceStock(Integer id, int quantity) {
        ProductDto product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        
        product.setStock(product.getStock() - quantity);
        product.setVersion(product.getVersion() + 1);
        return productRepository.update(product);
    }
}