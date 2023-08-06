package com.thefilipov.food.api.exceptionhandler;

import com.thefilipov.food.ApplicationConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ApiExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class ApiExceptionHandlerTest extends ApplicationConfigTest {

    @MockBean
    private MessageSource messageSource;

    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException, HttpHeaders, HttpStatus, WebRequest)}
     */
    @Test
    void testHandleHttpMediaTypeNotAcceptableContinue() {
        HttpMediaTypeNotAcceptableException ex = new HttpMediaTypeNotAcceptableException("https://example.org/example");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMediaTypeNotAcceptableResult = apiExceptionHandler
                .handleHttpMediaTypeNotAcceptable(ex, headers, HttpStatus.CONTINUE,
                        new ServletWebRequest(new MockHttpServletRequest()));
        assertNull(actualHandleHttpMediaTypeNotAcceptableResult.getBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleHttpMediaTypeNotAcceptableResult.getStatusCode());
        assertTrue(actualHandleHttpMediaTypeNotAcceptableResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException, HttpHeaders, HttpStatus, WebRequest)}
     */
    @Test
    void testHandleHttpMediaTypeNotAcceptableContinueMock() {
        HttpMediaTypeNotAcceptableException ex = mock(HttpMediaTypeNotAcceptableException.class);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMediaTypeNotAcceptableResult = apiExceptionHandler
                .handleHttpMediaTypeNotAcceptable(ex, headers, HttpStatus.CONTINUE,
                        new ServletWebRequest(new MockHttpServletRequest()));
        assertNull(actualHandleHttpMediaTypeNotAcceptableResult.getBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleHttpMediaTypeNotAcceptableResult.getStatusCode());
        assertTrue(actualHandleHttpMediaTypeNotAcceptableResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException, HttpHeaders, HttpStatus, WebRequest)}
     */
    @Test
    void testHandleHttpMediaTypeNotAcceptableSwitchingProtocols() {
        HttpMediaTypeNotAcceptableException ex = new HttpMediaTypeNotAcceptableException("https://example.org/example");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMediaTypeNotAcceptableResult = apiExceptionHandler
                .handleHttpMediaTypeNotAcceptable(ex, headers, HttpStatus.SWITCHING_PROTOCOLS,
                        new ServletWebRequest(new MockHttpServletRequest()));
        assertNull(actualHandleHttpMediaTypeNotAcceptableResult.getBody());
        assertEquals(HttpStatus.SWITCHING_PROTOCOLS, actualHandleHttpMediaTypeNotAcceptableResult.getStatusCode());
        assertTrue(actualHandleHttpMediaTypeNotAcceptableResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException, HttpHeaders, HttpStatus, WebRequest)}
     */
    @Test
    void testHandleHttpMediaTypeNotAcceptableProcessing() {
        HttpMediaTypeNotAcceptableException ex = new HttpMediaTypeNotAcceptableException("https://example.org/example");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMediaTypeNotAcceptableResult = apiExceptionHandler
                .handleHttpMediaTypeNotAcceptable(ex, headers, HttpStatus.PROCESSING,
                        new ServletWebRequest(new MockHttpServletRequest()));
        assertNull(actualHandleHttpMediaTypeNotAcceptableResult.getBody());
        assertEquals(HttpStatus.PROCESSING, actualHandleHttpMediaTypeNotAcceptableResult.getStatusCode());
        assertTrue(actualHandleHttpMediaTypeNotAcceptableResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException, HttpHeaders, HttpStatus, WebRequest)}
     */
    @Test
    void testHandleHttpMediaTypeNotAcceptableCheckpoint() {
        HttpMediaTypeNotAcceptableException ex = new HttpMediaTypeNotAcceptableException("https://example.org/example");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMediaTypeNotAcceptableResult = apiExceptionHandler
                .handleHttpMediaTypeNotAcceptable(ex, headers, HttpStatus.CHECKPOINT,
                        new ServletWebRequest(new MockHttpServletRequest()));
        assertNull(actualHandleHttpMediaTypeNotAcceptableResult.getBody());
        assertEquals(HttpStatus.CHECKPOINT, actualHandleHttpMediaTypeNotAcceptableResult.getStatusCode());
        assertTrue(actualHandleHttpMediaTypeNotAcceptableResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleUncaught(Exception, WebRequest)}
     */
    @Test
    void testHandleUncaught() {
        Exception ex = new Exception();
        ResponseEntity<Object> actualHandleUncaughtResult = apiExceptionHandler.handleUncaught(ex,
                new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleUncaughtResult.hasBody());
        assertTrue(actualHandleUncaughtResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleUncaughtResult.getStatusCode());
        assertEquals("Erro de sistema", ((Problem) actualHandleUncaughtResult.getBody()).getTitle());
        assertEquals(500, ((Problem) actualHandleUncaughtResult.getBody()).getStatus().intValue());
        assertNull(((Problem) actualHandleUncaughtResult.getBody()).getUserMessage());
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL,
                ((Problem) actualHandleUncaughtResult.getBody()).getDetail());
        assertNull(((Problem) actualHandleUncaughtResult.getBody()).getObjects());
        String expectedType = String.join("", "https://", System.getProperty("user.name"), "food.com.br/erro-de-sistema");
        assertEquals(expectedType, ((Problem) actualHandleUncaughtResult.getBody()).getType());
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleUncaught(Exception, WebRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleUncaught2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Exception.getMessage()" because "ex" is null
        //       at com.thefilipov.food.api.exceptionhandler.ApiExceptionHandler.handleUncaught(ApiExceptionHandler.java:67)
        //   See https://diff.blue/R013 to resolve this issue.

        apiExceptionHandler.handleUncaught(null, new ServletWebRequest(new MockHttpServletRequest()));
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleUncaught(Exception, WebRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleUncaught3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.web.context.request.WebRequest.setAttribute(String, Object, int)" because "request" is null
        //       at com.thefilipov.food.api.exceptionhandler.ApiExceptionHandler.handleExceptionInternal(ApiExceptionHandler.java:145)
        //       at com.thefilipov.food.api.exceptionhandler.ApiExceptionHandler.handleUncaught(ApiExceptionHandler.java:71)
        //   See https://diff.blue/R013 to resolve this issue.

        apiExceptionHandler.handleUncaught(new Exception(), null);
    }

    /**
     * Method under test: {@link ApiExceptionHandler#handleUncaught(Exception, WebRequest)}
     */
    @Test
    void testHandleUncaught4() {
        Exception ex = new Exception();
        WebRequest webRequest = mock(WebRequest.class);
        doNothing().when(webRequest).setAttribute((String) any(), (Object) any(), anyInt());
        ResponseEntity<Object> actualHandleUncaughtResult = apiExceptionHandler.handleUncaught(ex, webRequest);
        assertTrue(actualHandleUncaughtResult.hasBody());
        assertTrue(actualHandleUncaughtResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleUncaughtResult.getStatusCode());
        assertEquals("Erro de sistema", ((Problem) actualHandleUncaughtResult.getBody()).getTitle());
        assertEquals(500, ((Problem) actualHandleUncaughtResult.getBody()).getStatus().intValue());
        assertNull(((Problem) actualHandleUncaughtResult.getBody()).getUserMessage());
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL,
                ((Problem) actualHandleUncaughtResult.getBody()).getDetail());
        assertNull(((Problem) actualHandleUncaughtResult.getBody()).getObjects());
        String expectedType = String.join("", "https://", System.getProperty("user.name"), "food.com.br/erro-de-sistema");
        assertEquals(expectedType, ((Problem) actualHandleUncaughtResult.getBody()).getType());
        verify(webRequest).setAttribute((String) any(), (Object) any(), anyInt());
    }

}