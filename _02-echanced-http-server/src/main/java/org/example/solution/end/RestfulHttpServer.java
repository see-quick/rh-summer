package org.example.solution.end;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a simple RESTful HTTP server.
 * This server handles basic CRUD operations for items using a RESTful architecture.
 */
public class RestfulHttpServer {

    private static final int PORT = 8080;
    // Maps HTTP methods to a map of URL patterns and their corresponding handlers.
    private final Map<String, Map<Pattern, BiConsumer<HttpRequest, BufferedWriter>>> routes = new HashMap<>();

    // Simulating a simple database with concurrent hash map for thread safety
    private Map<Integer, String> items = new ConcurrentHashMap<>();
    private int currentId = 0;

    /**
     * The main method that starts the server.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            new RestfulHttpServer().startServer();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Constructs a new RestfulHttpServer and initializes the routes.
     */
    public RestfulHttpServer() {
        setupRoutes();
    }

    /**
     * Initializes the routes for the HTTP server.
     * This method maps HTTP methods and URL patterns to their respective handler functions.
     */
    private void setupRoutes() {
        routes.put("GET", new HashMap<>());
        routes.put("POST", new HashMap<>());
        routes.put("DELETE", new HashMap<>());

        // Regex to capture dynamic parts of the path
        routes.get("GET").put(Pattern.compile("^/items$"), this::handleGetItems);
        routes.get("GET").put(Pattern.compile("^/items/(\\d+)$"), this::handleGetItem);
        routes.get("POST").put(Pattern.compile("^/items$"), this::handlePostItem);
        routes.get("DELETE").put(Pattern.compile("^/items/(\\d+)$"), this::handleDeleteItem);
    }

    /**
     * Starts the server and listens for incoming connections on the specified port.
     * Handles incoming requests by dispatching them to the appropriate route handlers.
     *
     * @throws IOException  If an I/O error occurs when opening the socket.
     */
    private void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Listening for connection on port " + PORT + " ....");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientRequest(clientSocket);
                } catch (IOException e) {
                    System.err.println("Error handling client request: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Handles client requests by parsing the HTTP request and finding the appropriate route handler.
     *
     * @param clientSocket      The socket connected to the client.
     */
    private void handleClientRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            HttpRequest request = parseRequest(reader);
            boolean matched = false;
            Map<Pattern, BiConsumer<HttpRequest, BufferedWriter>> methodRoutes = routes.get(request.getMethod());

            for (Map.Entry<Pattern, BiConsumer<HttpRequest, BufferedWriter>> entry : methodRoutes.entrySet()) {
                Matcher matcher = entry.getKey().matcher(request.getPath());
                if (matcher.matches()) {
                    request.setPathParameters(matcher); // Assuming HttpRequest can store and provide path parameters
                    entry.getValue().accept(request, writer);
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                notFoundHandler(writer);
            }

            writer.flush();
        } catch (IOException e) {
            System.err.println("IO Exception in handling client request: " + e.getMessage());
        }
    }

    /**
     * Parses the HTTP request from the given BufferedReader.
     *
     * @param reader        The BufferedReader reading from the socket's input stream.
     * @return              A new HttpRequest object representing the parsed request.
     */
    private HttpRequest parseRequest(BufferedReader reader) {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            System.err.println("An error occurred:" + e.getMessage());
            throw new RuntimeException(e);
        }
        String[] requestLine = line.split(" ");
        String method = requestLine[0];
        String path = requestLine[1];

        // Skipping headers and other parts for simplicity
        return new HttpRequest(method, path);
    }

    private void handleGetItems(HttpRequest request, BufferedWriter writer) {
        System.out.println("GET items method invoked");

        String itemsJson = items.entrySet().stream()
            .map(e -> "\"" + e.getKey() + "\": \"" + e.getValue() + "\"")
            .reduce("{", (a, b) -> a + (a.equals("{") ? "" : ", ") + b) + "}";
        writeResponse(writer, itemsJson, "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n");
    }

    /**
     * Handles GET requests to retrieve all items.
     * Sends a JSON response containing all items in the 'items' map.
     *
     * @param request   The HttpRequest object containing request details.
     * @param writer    The BufferedWriter used to send the response to the client.
     */
    private void handleGetItem(HttpRequest request, BufferedWriter writer) {
        System.out.println("GET item method invoked");

        try {
            Integer id = Integer.parseInt(request.getPathParameter("param1")); // Assuming the first parameter is the item ID
            String item = items.get(id);
            if (item != null) {
                writeResponse(writer, "{\"item\": \"" + item + "\"}", "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n");
            } else {
                notFoundHandler(writer);
            }
        } catch (NumberFormatException e) {
            // Handle cases where the ID is not a valid integer
            notFoundHandler(writer);
        }
    }

    /**
     * Handles POST requests to create a new item.
     * Simulates item creation by incrementing an ID and adding the item to the 'items' map.
     * Sends a response indicating the creation of the item.
     *
     * @param request   The HttpRequest object containing request details.
     * @param writer    The BufferedWriter used to send the response to the client.
     */
    private void handlePostItem(HttpRequest request, BufferedWriter writer) {
        System.out.println("POST item method invoked");

        // Simulate creating an item
        items.put(++currentId, "Item " + currentId);
        writeResponse(writer, "Created item with id: " + currentId, "HTTP/1.1 201 Created\r\n");
    }

    /**
     * Handles DELETE requests to remove an item by its ID.
     * Removes the item from the 'items' map and sends an appropriate response.
     *
     * @param request   The HttpRequest object containing request details.
     * @param writer    The BufferedWriter used to send the response to the client.
     */
    private void handleDeleteItem(HttpRequest request, BufferedWriter writer) {
        System.out.println("DELETE item method invoked");

        try {
            Integer id = Integer.parseInt(request.getPathParameter("param1"));  // Retrieve the ID directly from pathParameters
            if (items.remove(id) != null) {
                writeResponse(writer, "Deleted item with id: " + id, "HTTP/1.1 200 OK\r\n");
            } else {
                notFoundHandler(writer);
            }
        } catch (NumberFormatException e) {
            // This block handles cases where the ID is not an integer
            System.err.println("Error parsing item ID: " + e.getMessage());
            notFoundHandler(writer);
        }
    }

    /**
     * Handles requests that do not match any registered route.
     * Sends a 404 Not Found response to the client.
     *
     * @param writer    The BufferedWriter used to send the response to the client.
     */
    private void notFoundHandler(BufferedWriter writer) {
        writeResponse(writer, "404 Not Found", "HTTP/1.1 404 Not Found\r\n");
    }

    /**
     * Writes a response to the client.
     * Constructs a response with the given body and status, and sends it using the provided BufferedWriter.
     *
     * @param writer    The BufferedWriter used to send the response.
     * @param body      The body of the response.
     * @param status    Optional array of strings specifying the status line; defaults to "HTTP/1.1 200 OK" if not provided.
     */
    private void writeResponse(BufferedWriter writer, String body, String... status) {
        String responseStatus = status.length > 0 ? status[0] : "HTTP/1.1 200 OK\r\n";
        try {
            writer.write(responseStatus + "Content-Type: text/html; charset=UTF-8\r\n\r\n" + body + "\n");
        } catch (IOException e) {
            System.err.println("An error occurred:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
