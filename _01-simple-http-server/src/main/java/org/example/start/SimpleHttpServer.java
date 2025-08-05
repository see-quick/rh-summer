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
        // 0-65535 ports, which can be allocated
        // reserved ports -> 80 -> HTTP, 443 -> HTTPS, 53 -> DNS server, 69 -> DHCP and more...
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Listening for connection on port 8080 ....");

            while (true) { // keep listening forever
                Socket clientSocket = serverSocket.accept();
                handleClientRequest(clientSocket);
            }
        }

        // ip-address:port
        // dns:port

        // 127.0.0.1:8080
        // localhost:8080
    }

    protected static void handleClientRequest(Socket clientSocket) {
        try (InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
             BufferedReader reader = new BufferedReader(isr);
             OutputStream os = clientSocket.getOutputStream()) {

            String line = reader.readLine(); // Read the request line

            // [DEBUG PURPOSE] 0. remove this while if you start after using testing curl :)
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }

            // 1. parsing path
                // a) GET /                 -> homepage
                // b) GET /about            -> about
                // c) GET /contact          -> contact
                // c) default               -> I don't understand with HTML :)
            // 2. building the HTTP response :)
            // 3. Bonus part
            //     (You could add validation part if other HTTP method was used then return ->
            //          HTTP/1.1 405 Method Not Allowed
            //          Content-Type: text/plain; charset=utf-8
            //          Content-Length: 18
            //
            //          Method Not Allowed
            // Send HTTP response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
