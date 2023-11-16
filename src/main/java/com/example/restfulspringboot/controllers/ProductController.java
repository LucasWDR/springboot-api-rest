package com.example.restfulspringboot.controllers;

import com.example.restfulspringboot.dtos.ProductRecordDto;
import com.example.restfulspringboot.models.ProductModel;
import com.example.restfulspringboot.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
     public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid
                                                         ProductRecordDto productRecordDto){
          var productModel = new ProductModel();
          BeanUtils.copyProperties(productRecordDto, productModel);
          return ResponseEntity.status(HttpStatus.CREATED)
                  .body(productService.addOrUpdateProduct(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getAllProducts());
    }
    @GetMapping("/products/{id}")
     public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> findProduct = productService.productById(id);
        if(findProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        findProduct.get().add(linkTo(methodOn(ProductController.class)
                .getAllProducts()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(findProduct.get());
    }

    @PutMapping("/products/{id}")
     public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id,
                                                 @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> findProduct = productService.productById(id);
        if(findProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
        }

        var productModel = findProduct.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.addOrUpdateProduct(productModel));

    }

    @DeleteMapping("/products/{id}")
     public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> findProduct = productService.productById(id);
        if(findProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
        }
        productService.deleteProduct(findProduct);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product deleted");
    }

}
