package com.thefilipov.food.infrastructure.service.email;

import org.junit.jupiter.api.BeforeEach;

class EmailExceptionTest {

    private EmailException emailExceptionUnderTest;

    @BeforeEach
    void setUp() {
        emailExceptionUnderTest = new EmailException("message", new Exception("message"));
    }
}
