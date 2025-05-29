package com.kurly.product.controller;

import com.kurly.product.dto.ApiResponse;
import com.kurly.product.dto.ProductRequest;
import com.kurly.product.dto.ProductResponse;
import com.kurly.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. 목록 조회 및 검색
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false) String name) {
        try {
            List<ProductResponse> products;
            if (name != null && !name.trim().isEmpty()) {
                // 검색
                products = productService.searchProductsByName(name.trim());
            } else {
                // 전체 목록 조회
                products = productService.getAllProducts();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. 상품 추가
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        try {
            ProductResponse product = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 3. 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        try {
            ProductResponse product = productService.updateProduct(id, request);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 4. 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(ApiResponse.success("상품이 삭제되었습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("상품을 찾을 수 없습니다.", "PRODUCT_NOT_FOUND"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("삭제 중 오류가 발생했습니다.", "DELETE_ERROR"));
        }
    }

    // 5. 단일 상품 조회 (추가 기능)
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        try {
            ProductResponse product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
