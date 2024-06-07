Certainly! Here's a complete, enhanced hands-on exercise for your students, formatted in Markdown:

```markdown
## Exercise: Enhance the Basic HTTP Server

### Objective
- Enhance the functionality of the basic HTTP server by adding support for different endpoints and customizing the response based on the request URL.

### Instructions

#### Task 1: Add Support for Multiple Endpoints
Modify the existing server code to handle requests to different paths, such as `/`, `/about`, and `/contact`.

```java
import java.io.*;
import java.net.Socket;

private static void handleClientRequest(Socket clientSocket) throws IOException {
    InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
    BufferedReader reader = new BufferedReader(isr);
    OutputStream os = clientSocket.getOutputStream();

    String requestLine = reader.readLine(); // Read the request line
    String requestPath = requestLine.split(" ")[1]; // Extract the path from request

    String httpResponse;
    switch (requestPath) {
        case "/":
            httpResponse = "HTTP/1.1 200 OK\r\n\r\n<html><body>Welcome to the Homepage!</body></html>";
            break;
        case "/about":
            httpResponse = "HTTP/1.1 200 OK\r\n\r\n<html><body>About Us</body></html>";
            break;
        case "/contact":
            httpResponse = "HTTP/1.1 200 OK\r\n\r\n<html><body>Contact Us</body></html>";
            break;
        default:
            httpResponse = "HTTP/1.1 404 Not Found\r\n\r\n<html><body>404 Page Not Found</body></html>";
            break;
    }
    os.write(httpResponse.getBytes("UTF-8"));
    os.close();
}
```

#### Task 2: Test Each Endpoint
Use a web browser or the `curl` command to test each of the new endpoints:
- For the homepage:
  ```bash
  curl http://localhost:8080/
  ```
- For the about page:
  ```bash
  curl http://localhost:8080/about
  ```
- For the contact page:
  ```bash
  curl http://localhost:8080/contact
  ```

#### Task 3: Add Your Own Endpoint
- Add an additional endpoint `/services` that describes the services offered by the server.
- Create a response HTML content for this new endpoint.
- Test the new endpoint using `curl` or a web browser.

### Discussion
- Discuss the challenges you faced while implementing the new endpoints.
- Explain how the server determines what content to send based on the URL accessed.
```