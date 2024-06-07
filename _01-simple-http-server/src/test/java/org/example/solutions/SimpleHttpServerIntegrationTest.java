package org.example.solutions;

import org.example.end.SimpleHttpServerWithGetMethods;
import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SimpleHttpServerIntegrationTest {

    private static Thread serverThread;
    private static final int SERVER_PORT = 8080;
    private static final String BASE_URL = "http://localhost:" + SERVER_PORT;

    @BeforeAll
    public static void setup() throws InterruptedException {
        serverThread = new Thread(() -> {
            try {
                SimpleHttpServerWithGetMethods.main(new String[]{}); // Start server
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start(); // Start the server in a separate thread
        Thread.sleep(2000);
    }

    @AfterAll
    public static void tearDown() {
        serverThread.interrupt(); // Stop the server
    }

    private String getServerResponse(String endpoint) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Check for non-200 responses and handle accordingly
        int status = connection.getResponseCode();
        InputStream inputStream = (status >= 200 && status < 300) ? connection.getInputStream() : connection.getErrorStream();

        String response = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        inputStream.close();
        connection.disconnect();

        return response;
    }

    @Test
    public void testHomePage() throws IOException {
        String response = getServerResponse("/");
        Assertions.assertTrue(response.contains("Welcome to the Homepage!"), "Expected homepage content");
    }

    @Test
    public void testAboutPage() throws IOException {
        String response = getServerResponse("/about");
        Assertions.assertTrue(response.contains("About Us Page"), "Expected about page content");
    }

    @Test
    public void testNotFoundPage() throws IOException {
        String response = getServerResponse("/nonexistent");
        Assertions.assertTrue(response.contains("404 Page Not Found"), "Expected 404 content");
    }
}
