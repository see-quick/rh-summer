package org.example.level2.end;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MediumHttpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8001);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try {
                    handleRequest(clientSocket);
                } catch (Exception e) {
                    System.err.println("Error handling request: " + e.getMessage());
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void handleRequest(Socket clientSocket) throws Exception {
        BufferedReader in = null;
        OutputStream out = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = clientSocket.getOutputStream();

            String line = in.readLine();
            while (line != null && !line.isEmpty()) {
                System.out.println(line);
                line = in.readLine();
            }

            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" +
                                  "<html><body><h1>Hello, World!</h1></body></html>";
            out.write(httpResponse.getBytes("UTF-8"));
            out.flush();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            clientSocket.close();
        }
    }
}
