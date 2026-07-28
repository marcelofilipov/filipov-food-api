package com.thefilipov.food.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FoodUtilsTest {

    @Test
    void containsTest() {
        String expression = "hamburger";
        String expectedExpression = "%hamburger%";

        String actualExpression = FoodUtils.contains(expression);

        assertEquals(expectedExpression, actualExpression);
    }
}