package org.example.end;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.Socket;

public class SimpleHttpServerWithGetMethodsMock {

    @Mock
    private Socket socketMock;

    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        outputStream = new ByteArrayOutputStream();

        // Initialize mock behavior for socket I/O streams
        when(socketMock.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    public void testHandleClientRequest_homePage() throws IOException {
        // Simulate receiving a GET request for home page
        String httpRequest = "GET / HTTP/1.1\r\n\r\n";
        inputStream = new ByteArrayInputStream(httpRequest.getBytes());
        when(socketMock.getInputStream()).thenReturn(inputStream);

        SimpleHttpServerWithGetMethods.handleClientRequest(socketMock);

        String response = outputStream.toString();
        assert(response.contains("HTTP/1.1 200 OK"));
        assert(response.contains("Welcome to the Homepage!"));
    }

    @Test
    public void testHandleClientRequest_aboutPage() throws IOException {
        // Simulate receiving a GET request for about page
        String httpRequest = "GET /about HTTP/1.1\r\n\r\n";
        inputStream = new ByteArrayInputStream(httpRequest.getBytes());
        when(socketMock.getInputStream()).thenReturn(inputStream);

        SimpleHttpServerWithGetMethods.handleClientRequest(socketMock);

        String response = outputStream.toString();
        assert(response.contains("HTTP/1.1 200 OK"));
        assert(response.contains("About Us Page"));
    }

    @Test
    public void testHandleClientRequest_notFound() throws IOException {
        // Simulate receiving a GET request for a non-existing page
        String httpRequest = "GET /nonexistent HTTP/1.1\r\n\r\n";
        inputStream = new ByteArrayInputStream(httpRequest.getBytes());
        when(socketMock.getInputStream()).thenReturn(inputStream);

        SimpleHttpServerWithGetMethods.handleClientRequest(socketMock);

        String response = outputStream.toString();
        assert(response.contains("HTTP/1.1 404 Not Found"));
        assert(response.contains("404 Page Not Found"));
    }
}
