package org.example.end;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleHttpServerWithGetMethodsTest {

    @Test
    void testGetRequestPath() {
        // Simulating a typical HTTP request line input
        String requestLine = "GET / HTTP/1.1";
        String path = SimpleHttpServerWithGetMethods.getRequestPath(requestLine);
        Assertions.assertEquals("/", path);

        requestLine = "GET /about HTTP/1.1";
        path = SimpleHttpServerWithGetMethods.getRequestPath(requestLine);
        Assertions.assertEquals("/about", path);

        requestLine = "GET /nonexistent HTTP/1.1";
        path = SimpleHttpServerWithGetMethods.getRequestPath(requestLine);
        Assertions.assertEquals("/nonexistent", path);

        // Edge case: empty or null input
        requestLine = "";
        path = SimpleHttpServerWithGetMethods.getRequestPath(requestLine);
        Assertions.assertEquals("", path);

        path = SimpleHttpServerWithGetMethods.getRequestPath(null);
        Assertions.assertEquals("", path);
    }

    @Test
    void testBuildResponseForPath() {
        String response;

        response = SimpleHttpServerWithGetMethods.buildResponseForPath("/");
        Assertions.assertTrue(response.contains("Welcome to the Homepage!"));
        Assertions.assertTrue(response.startsWith("HTTP/1.1 200 OK"));

        response = SimpleHttpServerWithGetMethods.buildResponseForPath("/about");
        Assertions.assertTrue(response.contains("About Us Page"));
        Assertions.assertTrue(response.startsWith("HTTP/1.1 200 OK"));

        response = SimpleHttpServerWithGetMethods.buildResponseForPath("/contact");
        Assertions.assertTrue(response.contains("Contact Us Page"));
        Assertions.assertTrue(response.startsWith("HTTP/1.1 200 OK"));

        response = SimpleHttpServerWithGetMethods.buildResponseForPath("/nonexistent");
        Assertions.assertTrue(response.contains("404 Page Not Found"));
        Assertions.assertTrue(response.startsWith("HTTP/1.1 404 Not Found"));
    }
}
