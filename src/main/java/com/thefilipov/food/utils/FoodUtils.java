package com.thefilipov.food.utils;

import java.text.MessageFormat;

public class FoodUtils {

    public static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

}
