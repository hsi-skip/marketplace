package com.kata.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kata.marketplace.entity.enums.ProductStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    @Column(name = "product_image")
    String productImage;

    @Column
    double price;

    @Enumerated(EnumType.STRING)
    ProductStatusEnum publish = ProductStatusEnum.PUBLISH;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;
}
