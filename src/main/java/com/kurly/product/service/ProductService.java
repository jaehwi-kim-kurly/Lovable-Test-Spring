package com.kurly.product.service;

import com.kurly.product.dto.ProductRequest;
import com.kurly.product.dto.ProductResponse;
import com.kurly.product.entity.Product;
import com.kurly.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 모든 상품 조회
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    // 이름으로 상품 검색
    public List<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    // 상품 생성
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(
                request.getName(),
                request.getPrice(),
                request.getStock(),
                request.getDescription()
        );
        Product savedProduct = productRepository.save(product);
        return new ProductResponse(savedProduct);
    }

    // 상품 수정
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            product.setStock(request.getStock());
            product.setDescription(request.getDescription());
            Product updatedProduct = productRepository.save(product);
            return new ProductResponse(updatedProduct);
        }
        throw new RuntimeException("상품을 찾을 수 없습니다. ID: " + id);
    }

    // 상품 삭제
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("상품을 찾을 수 없습니다. ID: " + id);
        }
    }

    // 상품 조회 (단일)
    public ProductResponse getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return new ProductResponse(product.get());
        }
        throw new RuntimeException("상품을 찾을 수 없습니다. ID: " + id);
    }
}
