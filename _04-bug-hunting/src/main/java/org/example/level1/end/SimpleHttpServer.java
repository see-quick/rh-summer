package org.example.level1.end;

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
        BufferedReader in = null;
        OutputStream out = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = clientSocket.getOutputStream();

            String line = in.readLine();
            while (line != null && !line.isEmpty()) {  // Fix: Ensure line is not null to prevent NPE.
                System.out.println(line);
                line = in.readLine();
            }

            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" +
                                  "<html><body><h1>Hello, World!</h1></body></html>";
            out.write(httpResponse.getBytes("UTF-8"));
            out.flush();
        } finally {
            // Fix: In, Out and closing Socket
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
