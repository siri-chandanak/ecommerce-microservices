package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product create(@RequestBody ProductDto dto)
    {
        return productService.create(dto);
    }

    @GetMapping
    public Page<Product> getProducts(@RequestParam int page, @RequestParam int size)
    {
        return productService.getProducts(page, size);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String name)
    {
        return productService.search(name);
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable UUID id)
    {
        return productService.getById(id);
    }
}
