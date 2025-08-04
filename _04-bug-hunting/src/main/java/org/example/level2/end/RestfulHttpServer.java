package org.example.level2.end;

import org.example.level2.start.HttpRequest;

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
 */
public class RestfulHttpServer {

    private static final int PORT = 8080;
    private final Map<String, Map<Pattern, BiConsumer<HttpRequest, BufferedWriter>>> routes = new HashMap<>();

    private Map<Integer, String> items = new ConcurrentHashMap<>();
    private int currentId = 0;

    public static void main(String[] args) {
        try {
            new RestfulHttpServer().startServer();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public RestfulHttpServer() {
        setupRoutes();
    }

    private void setupRoutes() {
        routes.put("GET", new HashMap<>());
        routes.put("POST", new HashMap<>());
        routes.put("DELETE", new HashMap<>());

        // BUG 1: ...
        routes.get("GET").put(Pattern.compile("^/items$"), this::handleGetItems);
        routes.get("GET").put(Pattern.compile("^/items/(\\d+)$"), this::handleGetItem);
        routes.get("POST").put(Pattern.compile("^/items$"), this::handlePostItem);
        routes.get("DELETE").put(Pattern.compile("^/items/(\\d+)$"), this::handleDeleteItem);
    }

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

    private void handleClientRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            HttpRequest request = parseRequest(reader);
            boolean matched = false;
            Map<Pattern, BiConsumer<HttpRequest, BufferedWriter>> methodRoutes = routes.get(request.getMethod());

            for (Map.Entry<Pattern, BiConsumer<HttpRequest, BufferedWriter>> entry : methodRoutes.entrySet()) {
                Matcher matcher = entry.getKey().matcher(request.getPath());
                if (matcher.matches()) {
                    request.setPathParameters(matcher);
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

        // BUG 2: Method is always set to "GET" regardless of the request
        // return new HttpRequest("GET", path); // BAD: always GET
        return new HttpRequest(method, path);
    }

    private void handleGetItems(HttpRequest request, BufferedWriter writer) {
        System.out.println("GET items method invoked");

        // BUG 3: JSON formatting error - always adds a trailing comma
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<Integer, String> e : items.entrySet()) {
            sb.append("\"").append(e.getKey()).append("\": \"").append(e.getValue()).append("\",");
        }
        sb.append("}");
        writeResponse(writer, sb.toString(), "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n");
    }

    private void handleGetItem(HttpRequest request, BufferedWriter writer) {
        System.out.println("GET item method invoked");
        try {
            Integer id = Integer.parseInt(request.getPathParameter("param1"));
            String item = items.get(id);
            if (item != null) {
                writeResponse(writer, "{\"item\": \"" + item + "\"}", "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n");
            } else {
                notFoundHandler(writer);
            }
        } catch (NumberFormatException e) {
            notFoundHandler(writer);
        }
    }

    private void handlePostItem(HttpRequest request, BufferedWriter writer) {
        System.out.println("POST item method invoked");
        items.put(++currentId, "Item " + currentId);
        writeResponse(writer, "Created item with id: " + currentId, "HTTP/1.1 201 Created\r\n");
    }

    private void handleDeleteItem(HttpRequest request, BufferedWriter writer) {
        System.out.println("DELETE item method invoked");
        try {
            Integer id = Integer.parseInt(request.getPathParameter("param1"));
            if (items.remove(id) != null) {
                writeResponse(writer, "Deleted item with id: " + id, "HTTP/1.1 200 OK\r\n");
            } else {
                notFoundHandler(writer);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing item ID: " + e.getMessage());
            notFoundHandler(writer);
        }
    }

    // Bug 4
    private void notFoundHandler(BufferedWriter writer) {
        writeResponse(writer, "404 Not Found", "HTTP/1.1 404 Not Found\r\n");
    }

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