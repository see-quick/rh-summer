package org.example.level1.end;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * A simple HTTP server that handles GET requests for predefined paths.
 */
public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Listening for connection on port 8080 ....");
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientRequest(clientSocket);
                }
            }
        }
    }

    protected static void handleClientRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream os = clientSocket.getOutputStream()) {

            String line = reader.readLine(); // Read the request line
            String requestPath = getRequestPath(line);

            // Write the entire HTTP response (BUG 1 fixed)
            os.write(buildResponseForPath(requestPath).getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static String getRequestPath(String requestLine) {
        if (requestLine != null && !requestLine.isEmpty()) {
            // Typical Request Line format: "GET /path HTTP/1.1"
            String[] parts = requestLine.split(" ");
            if (parts.length > 1) {
                return parts[1]; // BUG 2 fixed: correct part for the path
            }
        }
        return "";
    }

    protected static String buildResponseForPath(String path) {
        switch (path) {
            case "/":
                return "HTTP/1.1 200 OK\r\n\r\n<html><body>Welcome to the Homepage!</body></html>\n";
            case "/about": // BUG 3 fixed: corrected path spelling
                return "HTTP/1.1 200 OK\r\n\r\n<html><body>About Us Page</body></html>\n";
            case "/contact":
                return "HTTP/1.1 200 OK\r\n\r\n<html><body>Contact Us Page</body></html>\n";
            default:
                return "HTTP/1.1 404 Not Found\r\n\r\n<html><body>404 Page Not Found</body></html>\n";
        }
    }
}