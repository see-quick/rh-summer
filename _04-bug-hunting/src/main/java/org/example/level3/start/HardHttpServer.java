package org.example.level3.start;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HardHttpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8002);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try {
                    handleRequest(clientSocket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void handleRequest(Socket clientSocket) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        String line = in.readLine();
        StringBuilder request = new StringBuilder();
        while (!line.isEmpty()) {
            request.append(line).append("\n");
            line = in.readLine();
        }

        // Bug: Potentially missing security checks on the request.
        System.out.println(request.toString());

        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" +
                              "<html><body><h1>Hello, World!</h1></body></html>";
        out.write(httpResponse.getBytes("UTF-8"));

        in.close();  // Bug: Doesn't close output stream.
        out.close();

        clientSocket.close();
    }
}
