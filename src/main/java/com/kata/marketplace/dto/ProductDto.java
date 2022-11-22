package com.kata.marketplace.dto;

import com.kata.marketplace.entity.enums.ProductStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    Long id;
    String title;
    String description;
    String productImage;
    double price;
    ProductStatusEnum publish;
    UserDto owner;
}
