package com.kata.marketplace.entity;

import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

class EntityTest {

    @Test
    void testEntities() {
        assertPojoMethodsForAll(Product.class, User.class)
                .quickly()
                .testing(Method.CONSTRUCTOR)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .areWellImplemented();
    }
}
