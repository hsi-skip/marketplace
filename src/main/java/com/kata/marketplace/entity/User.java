package com.kata.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(unique = true)
    String username;
    String pseudonym;
    String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Product> products;
}
