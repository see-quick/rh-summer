package org.example.end;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * A simple HTTP server that handles GET requests for predefined paths.
 */
public class SimpleHttpServerWithGetMethods {

    /**
     * Main method to start the HTTP server.
     * Listens on port 8080 and handles incoming client connections.
     *
     * @param args              Command line arguments (not used).
     * @throws IOException      If an I/O error occurs when opening the server socket.
     */
    public static void main(String[] args) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Listening for connection on port 8080 ....");
            while (true) { // Keep listening indefinitely
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientRequest(clientSocket);
                }
            }
        }
    }

    /**
     * Handles client requests by reading the HTTP request line and generating an appropriate response.
     *
     * @param clientSocket      The client socket connected to the server.
     */
    protected static void handleClientRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream os = clientSocket.getOutputStream()) {

            String line = reader.readLine(); // Read the request line
            String requestPath = getRequestPath(line);

            // Determine response based on the request path
            String httpResponse = buildResponseForPath(requestPath);
            os.write(httpResponse.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts the request path from the HTTP request line.
     * @param requestLine   The HTTP request line (e.g., "GET /path HTTP/1.1").
     * @return              The path part of the request line.
     */
    protected static String getRequestPath(String requestLine) {
        if (requestLine != null && !requestLine.isEmpty()) {
            // Typical Request Line format: "GET /path HTTP/1.1"
            String[] parts = requestLine.split(" ");
            if (parts.length > 1) {
                return parts[1]; // This is the path part of the request line
            }
        }
        return ""; // Return empty string if no path is found
    }

    /**
     * Builds an HTTP response based on the given request path.
     * @param path      The request path.
     * @return          A string representing the full HTTP response including the status line, headers, and body.
     */
    protected static String buildResponseForPath(String path) {
        switch (path) {
            case "/":
                return "HTTP/1.1 200 OK\r\n\r\n<html><body>Welcome to the Homepage!</body></html>\n";
            case "/about":
                return "HTTP/1.1 200 OK\r\n\r\n<html><body>About Us Page</body></html>\n";
            case "/contact":
                return "HTTP/1.1 200 OK\r\n\r\n<html><body>Contact Us Page</body></html>\n";
            default:
                return "HTTP/1.1 404 Not Found\r\n\r\n<html><body>404 Page Not Found</body></html>\n";
        }
    }
}
