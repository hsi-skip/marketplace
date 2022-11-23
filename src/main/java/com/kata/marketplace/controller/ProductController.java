package com.kata.marketplace.controller;

import com.kata.marketplace.dto.ProductDto;
import com.kata.marketplace.entity.enums.ProductStatusEnum;
import com.kata.marketplace.repository.UserRepository;
import com.kata.marketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/all")
    public ResponseEntity getAll() {
        logger.debug("Requested to get all products");
        List<ProductDto> productsDto = productService.getAll();

        if (productsDto.isEmpty())
            return new ResponseEntity<>("No record found", HttpStatus.NOT_FOUND);
         else
            return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity getProductById(@PathVariable("id") long idProduct) {
        logger.debug("Requested to get product by id {} ", idProduct);
        Optional<ProductDto> productsDto = productService.getById(idProduct);

        return productsDto.map(productDto -> new ResponseEntity<>(productDto, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity("Product not found", HttpStatus.NOT_FOUND));

    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto) {
        logger.debug("Requested to get add product [{}] ", productDto);
//        try{
            productService.save(productDto);
            return new ResponseEntity<>("Product saved successfully", HttpStatus.OK);
//        }catch (IllegalArgumentException e){
//            logger.error("IllegalArgumentException: {}", e);
//            return new ResponseEntity<>("Bad request, product not added", HttpStatus.BAD_REQUEST);
//        }catch (Exception e){
//            logger.error("Exception: {}", e);
//            return new ResponseEntity<>("Internal server error, product not added", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestParam("id") long idProduct, @RequestBody ProductDto productDto) {
        logger.debug("Requested to update product [id: {}] by [{}] ", idProduct, productDto);
        try{
            productService.update(idProduct, productDto);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }catch (IllegalArgumentException e){
            logger.error("IllegalArgumentException: {}", e);
            return new ResponseEntity<>("Bad request, product not updated", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Exception: {}", e);
            return new ResponseEntity<>("Internal server error, product not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/publish")
    public ResponseEntity<String> changeStatutProduct(@RequestParam("id") long idProduct, @RequestParam("published") ProductStatusEnum published) {
        logger.debug("Requested to change status of product [id: {}] to {} ", idProduct, published.label);
        try{
            productService.changeStatus(idProduct, published);
            return new ResponseEntity<>("Product " + published.label + " successfully", HttpStatus.OK);

        }catch (IllegalArgumentException e){
            logger.error("IllegalArgumentException: {}", e);
            return new ResponseEntity<>("Bad request, Status not changed", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Exception: {}", e);
            return new ResponseEntity<>("Internal server error, Status not changed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
