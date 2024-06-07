package org.example.start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Listening for connection on port 8080 ....");

            while (true) { // keep listening forever
                Socket clientSocket = serverSocket.accept();
                handleClientRequest(clientSocket);
            }
        }
    }

    protected static void handleClientRequest(Socket clientSocket) {
        try (InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
             BufferedReader reader = new BufferedReader(isr);
             OutputStream os = clientSocket.getOutputStream()) {

            String line = reader.readLine(); // Read the request line
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }

            // Send HTTP response
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<html><body>Hello World!</body></html>\n";
            os.write(httpResponse.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
