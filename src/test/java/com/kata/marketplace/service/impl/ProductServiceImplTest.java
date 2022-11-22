package com.kata.marketplace.service.impl;

import com.kata.marketplace.dto.ProductDto;
import com.kata.marketplace.entity.Product;
import com.kata.marketplace.entity.enums.ProductStatusEnum;
import com.kata.marketplace.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    @MockitoSettings(strictness = Strictness.LENIENT)

    class ProductServiceImplTest {

        ProductServiceImpl productService;

        @Mock ProductRepository productRepository;
        @Mock ModelMapper modelMapper;

        Long productId = 1L;

        @BeforeEach
        void setUp(){
            productService = new ProductServiceImpl(productRepository, modelMapper);
        }

        @Test
        void testGetAllProducts(){
            productService.getAll();
            verify(productRepository).findAll();
        }

        @Test
        void testGetProductById(){
            Product product = new Product();
            product.setId(productId);

            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            productService.getById(productId);
            verify(productRepository).findById(productId);
        }

        @Test
        void testSaveProduct(){
            ProductDto productDto = new ProductDto();
            productDto.setId(productId);

            Product product = new Product();
            product.setId(productId);

            when(modelMapper.map(productDto, Product.class)).thenReturn(product);

            productService.save(productDto);
            verify(productRepository).save(product);
        }

        @Test
        void testUpdateProduct(){
            Product product = new Product();
            product.setId(productId);
            product.setTitle("Title");

            ProductDto productDto = new ProductDto();
            productDto.setId(productId);
            productDto.setTitle("Title");

            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

            productService.update(productId, productDto);
            verify(productRepository).findById(productId);
        }

        @Test
        void testUpdateProductWhenIllegalArgumentException(){
            ProductDto productDto = new ProductDto();
            productDto.setId(productId);

            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
            assertThatThrownBy(() -> productService.update(productId, productDto))
                    .isInstanceOf(IllegalArgumentException.class);

            verify(productRepository, times(1)).findById(productId);
        }

        @Test
        void testChangeStatus(){
            Product product = new Product();
            product.setId(productId);
            product.setTitle("Title");

            ProductDto productDto = new ProductDto();
            productDto.setId(productId);
            productDto.setTitle("Title");

            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

            productService.changeStatus(productId, ProductStatusEnum.PUBLISH);
            verify(productRepository).findById(productId);
        }

    }
