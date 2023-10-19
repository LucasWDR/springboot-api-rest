package com.example.restfulspringboot.controllers;

import com.example.restfulspringboot.dtos.ProductRecordDto;
import com.example.restfulspringboot.models.ProductModel;
import com.example.restfulspringboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
     public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
          var productModel = new ProductModel();
          BeanUtils.copyProperties(productRecordDto, productModel);
          return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
     public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
     public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> findProduct = productRepository.findById(id);
        if(findProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(findProduct.get());
    }

    @PutMapping("/products/{id}")
     public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id,
                                                 @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> findProduct = productRepository.findById(id);
        if(findProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        var productModel = findProduct.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));

    }

    @DeleteMapping("/products/{id}")
     public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> findProduct  = productRepository.findById(id);
        if(findProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productRepository.delete(findProduct.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");

    }

}
