package com.example.restfulspringboot.services;


import com.example.restfulspringboot.controllers.ProductController;
import com.example.restfulspringboot.models.ProductModel;
import com.example.restfulspringboot.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    final ProductRepository productRepository;

    public  ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }


    public ProductModel getAllProducts(){
        List<ProductModel> productsList  = productRepository.findAll();
        if(!productsList.isEmpty()){
            for(ProductModel product: productsList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return (ProductModel) productsList;
    }

    public ProductModel addProduct(ProductModel productModel) {
        return productRepository.save(productModel);
    }

}
