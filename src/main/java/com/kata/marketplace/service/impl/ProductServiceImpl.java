package com.kata.marketplace.service.impl;

import com.kata.marketplace.dto.ProductDto;
import com.kata.marketplace.entity.Product;
import com.kata.marketplace.entity.enums.ProductStatusEnum;
import com.kata.marketplace.repository.ProductRepository;
import com.kata.marketplace.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> getById(long idProduct) {
        return productRepository.findById(idProduct).map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public void save(ProductDto productDto) {
        productRepository.save(modelMapper.map(productDto, Product.class));
    }

    @Override
    public void update(Long productId, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        optionalProduct.ifPresentOrElse(
                product -> {
                    product.setTitle(productDto.getTitle());
                    product.setDescription(productDto.getDescription());
                    product.setPrice(productDto.getPrice());
                    product.setProductImage(productDto.getProductImage());
                    save(modelMapper.map(product, ProductDto.class));
                },

                () -> { throw new IllegalArgumentException(); });
    }

    @Override
    public void changeStatus(long productId, ProductStatusEnum published) {
        Optional<Product> product = productRepository.findById(productId);
        product.ifPresent(product1 -> {
            product1.setPublish(published);
            this.save(modelMapper.map(product.get(), ProductDto.class));
        });
    }

}
