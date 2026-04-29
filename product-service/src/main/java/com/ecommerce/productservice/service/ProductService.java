package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create(ProductDto dto)
    {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public Page<Product> getProducts(int page, int size)
    {
        return productRepository.findAll(PageRequest.of(page,size));
    }

    public List<Product> search(String name)
    {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product getById(UUID id)
    {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
