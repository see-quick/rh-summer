## 2. Enhancing the Basic HTTP Server

### Objective
- Extend the basic HTTP server to handle different types of HTTP requests (GET, POST) and route them to different endpoints.

### Content

#### Step 1: Enhance Server Socket Setup
- Modify the existing server code to route requests based on the method and path.
- Introduce a simple routing mechanism.

```java
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EnhancedHttpServer {
  public static void main(String[] args) {
    ServerSocket serverSocket = new ServerSocket(8080);
    System.out.println("Listening for connection on port 8080 ....");

    while (true) {
      try (Socket clientSocket = serverSocket.accept()) {
        handleClientRequest(clientSocket);
      }
    }
  }

  private static void handleClientRequest(Socket clientSocket) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

    String line = reader.readLine();
    if (line != null) {
      String[] requestLine = line.split(" ");
      String method = requestLine[0];
      String path = requestLine[1];

      switch (method) {
        case "GET":
          handleGetRequest(path, writer);
          break;
        case "POST":
          handlePostRequest(path, reader, writer);
          break;
        default:
          String response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";
          writer.write(response);
          break;
      }
      writer.flush();
    }
  }

  private static void handleGetRequest(String path, BufferedWriter writer) {
    String content = "HTTP/1.1 200 OK\r\n\r\n<html><body>GET request for " + path + "</body></html>";
    writer.write(content);
  }

  private static void handlePostRequest(String path, BufferedReader reader, BufferedWriter writer) {
    // Read the post data
    StringBuilder payload = new StringBuilder();
    while (reader.ready()) {
      payload.append((char) reader.read());
    }

    String content = "HTTP/1.1 200 OK\r\n\r\n<html><body>POST request for " + path + " with data: " + payload + "</body></html>";
    writer.write(content);
  }
}
```

#### Step 2: Running and Testing Enhanced Server
- Run the EnhancedHttpServer class.
- Use curl or a similar tool to send GET and POST requests:
  - For a GET request:
    - `curl http://localhost:8080/welcome`
  - For a POST request
    - `curl -X POST -d "This is sample data" http://localhost:8080/data`
- Verify the server handles each type of request appropriately and responds with the correct content.

#### Step 3: Discussion and Experimentation
- Discuss the routing mechanism and how it's implemented.
- Explore handling additional HTTP methods and adding more complex routing logic.
- Consider error handling and response status codes to mimic a real-world server environment more closely.