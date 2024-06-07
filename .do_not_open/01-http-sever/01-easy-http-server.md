## 1. Creating a Basic HTTP Server

### Objective
- Learn to build a simple HTTP server that can handle basic requests.

### Content

#### Step 1: Setting Up the Java Environment
- Ensure Java Development Kit (JDK) is installed.
- Set up a Java project in your preferred IDE (Eclipse, IntelliJ IDEA, etc.).

#### Step 2: Writing the Server Code
- **Create a Server Socket:**

```java
  import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = new ServerSocket(8080); // Listen on port 8080
        System.out.println("Listening for connection on port 8080 ....");
        while (true) { // keep listening forever
            Socket clientSocket = serverSocket.accept();
            handleClientRequest(clientSocket);
        }
    }

    private static void handleClientRequest(Socket clientSocket) {
        try (InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
             BufferedReader reader = new BufferedReader(isr);
             OutputStream os = clientSocket.getOutputStream()) {

            String line = reader.readLine(); // Read the request line
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }

            // Send HTTP response
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<html><body>Hello World!</body></html>";
            os.write(httpResponse.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Step 3: Running and Testing the Server

- Run the HttpServer class. 
- Open a web browser and visit http://localhost:8080, or use a tool like curl to test the server:

```bash
curl http://localhost:8080
```

- You should see the output Hello World! in your browser or terminal.


