package com.kata.marketplace.service;

import com.kata.marketplace.dto.ProductDto;
import com.kata.marketplace.entity.enums.ProductStatusEnum;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> getAll();

    Optional<ProductDto> getById(long idProduct);

    void save(ProductDto productDto);

    void update(Long productId, ProductDto productDto);

    void changeStatus(long productId, ProductStatusEnum published);
}
