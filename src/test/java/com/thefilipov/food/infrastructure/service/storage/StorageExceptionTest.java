package com.thefilipov.food.infrastructure.service.storage;

import org.junit.jupiter.api.BeforeEach;

class StorageExceptionTest {

    private StorageException storageExceptionUnderTest;

    @BeforeEach
    void setUp() {
        storageExceptionUnderTest = new StorageException("message", new Exception("message"));
    }
}
