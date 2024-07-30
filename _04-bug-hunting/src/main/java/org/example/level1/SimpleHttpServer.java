package org.example.level1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        String line;
        StringBuilder request = new StringBuilder();
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            request.append(line).append("\n");
            System.out.println(line);
        }

        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" +
                              "<html><body><h1>Hello, World!</h1></body></html>";
        out.write(httpResponse.getBytes("UTF-8"));
    }
}
