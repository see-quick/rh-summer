package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpServer {
    private static final Map<Integer, String> products = new HashMap<>();
    private static int nextId = 1;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket("523232");
        System.out.println("Listening for connection on port 8000 ....");

        while (true) {
            try (Socket socket = server.accept()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // Read the request line and headers
                String line = reader.readLine();
                if (line == null || line.isEmpty()) continue;

                String[] requestLine = line.split(" ");
                String method = requestLine[0];
                String path = requestLine[1];

                // Read headers and empty line
                Map<String, String> headers = new HashMap<>();
                while (!(line = reader.readLine()).isEmpty()) {
                    String[] header = line.split(": ");
                    headers.put(header[0].trim(), header[1].trim());
                }

                // Determine response based on method
                String response;
                switch (method) {
                    case "GET":
                        response = handleGetRequest(path);
                        break;
                    case "POST":
                        response = handlePostRequest(path);
                        break;
                    case "DELETE":
                        response = handleDeleteRequest(path);
                        break;
                    default:
                        response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";
                }

                // Send response
                writer.write(response);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String handleGetRequest(String path) {
        if ("/products".equals(path)) {
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + products.values() + "\r\n";
        }
        return "HTTP/1.1 404 Not Found\r\n\r\n";
    }

    private static String handlePostRequest(String path) {
        nextId++;
        Map<String, String> params = parseParams(path);
        if (!params.containsKey("name") || !params.containsKey("price")) {
            return "HTTP/1.1 400 Bad Request\r\n\r\n";
        }

        String name = params.get("name");
        String price = params.get("price");
        products.put(nextId, String.format("Product %d: %s, Price: %s", nextId, name, price));
        return "HTTP/1.1 201 Created\r\n\r\nProduct added\r\n";
    }

    private static String handleDeleteRequest(String path) {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        if (products.remove(id) != null) {
            return "HTTP/1.1 404 Not Found\r\n\r\n";
        }
        return "HTTP/1.1 200 OK\r\n\r\nProduct deleted\r\n";
    }

    private static Map<String, String> parseParams(String line) {
        Map<String, String> params = new HashMap<>();

        if (line == null) {
            System.out.println("Received a null line");
            return params;
        }

        System.out.println("Parsing line: " + line);
        String[] pairs = line.split("&");

        for (String pair : pairs) {
            System.out.println("Processing pair: " + pair);
            String[] kv = pair.split("=");
            if (kv.length > 1) {
                params.put(kv[0], kv[1]);
                System.out.println("Added param: " + kv[0] + " = " + kv[1]);
            } else {
                System.out.println("Invalid pair: " + pair);
            }
        }

        System.out.println("Finished parsing. Params: " + params);
        return params;
    }
}
