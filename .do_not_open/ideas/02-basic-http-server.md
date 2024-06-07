# Session: Building a Simple HTTP Server in Java

## Objective:
Learn the fundamentals of HTTP servers by developing a simple web server in Java that can handle basic requests.

## Tools and Technologies:
- **Java JDK:** Ensure Java Development Kit (JDK) is installed and properly set up on all participants' machines.
- **IDE:** Any Java IDE like IntelliJ IDEA, Eclipse, or NetBeans.
- **HTTP Library:** Use Java's built-in `java.net` package which provides support for creating HTTP servers without external libraries.

## Mini-Lecture Topics:

### 1. Introduction to HTTP Servers:
   - Explain what HTTP servers are and their role in web development.
   - Brief overview of the HTTP protocol, request/response model, and common HTTP methods (GET, POST).

### 2. Basics of Java Networking:
   - Introduction to Java's networking capabilities, focusing on the `java.net` package.
   - Discuss how to use Java sockets for network communication.

### 3. Handling HTTP Requests in Java:
   - Discuss how to interpret HTTP requests and send appropriate responses using Java.
   - Overview of creating a simple server that listens on a port, accepts connections, and reads/writes data.

## Coding Activity:

### 1. Setting Up the Java Project:
   - Create a new Java project in the chosen IDE.
   - Ensure the JDK is properly configured for the project.

### 2. Implementing the HTTP Server:
   - Write a Java class that opens a server socket and listens for incoming connections.
   ```java
   ServerSocket serverSocket = new ServerSocket(8080);
   System.out.println("Listening for connection on port 8080 ....");
   ```
   - Accept connections and process requests in a loop. For each connection, read the HTTP request, parse it, and send a simple HTML response.
   ```java
   try (Socket clientSocket = serverSocket.accept()) {
       InputStreamReader isr =  new InputStreamReader(clientSocket.getInputStream());
       BufferedReader reader = new BufferedReader(isr);
       String line = reader.readLine(); // Read the request line
       while (!line.isEmpty()) {
           System.out.println(line);
           line = reader.readLine();
       }

       // Send response
       String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<html><body>Hello World!</body></html>";
       clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
   }
   ```

### 3. Running and Testing the Server:
   - Run the server application and use a web browser or a tool like `curl` to test sending requests to your server.
   - Discuss how the server handles different types of requests and how it could be extended to serve actual files or support more complex routing and processing.

## Wrap-Up and Discussion:

- **Review the Session's Work:** Summarize what was accomplished and discuss any issues participants encountered during implementation.
- **Exploring Further:** Introduce concepts for extending the server, such as handling POST requests, serving static files, or implementing a basic REST API.
- **Q&A:** Address any questions or clarifications needed about HTTP servers, Java networking, or related topics.

## Homework/Extension Activities:

- **Enhance the Server:** Challenge participants to extend the server's capabilities, such as supporting more HTTP methods, handling JSON payloads, or adding simple routing.
- **Experiment with Frameworks:** Encourage exploring Java web frameworks like Spring Boot or Jakarta EE to understand how they simplify web server and application development.

## Preparation for Next Session:
Briefly introduce the next session's focus, potentially moving towards developing a full-fledged web application using Java and a popular framework to apply the principles learned about HTTP servers in a more complex project.
```