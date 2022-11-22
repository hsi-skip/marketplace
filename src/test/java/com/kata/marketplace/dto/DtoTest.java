package com.kata.marketplace.dto;

import com.kata.marketplace.entity.User;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

class DtoTest {

    @Test
    void testEntities() {
        assertPojoMethodsForAll(ProductDto.class, UserDto.class, AuthDto.class)
                .quickly()
                .testing(Method.CONSTRUCTOR)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .areWellImplemented();
    }
}
