package com.example.restfulspringboot.controllers;

import com.example.restfulspringboot.dtos.ProductRecordDto;
import com.example.restfulspringboot.models.ProductModel;
import com.example.restfulspringboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
