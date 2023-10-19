package com.example.restfulspringboot.repositories;

import com.example.restfulspringboot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository  extends JpaRepository<ProductModel, UUID> {

}
