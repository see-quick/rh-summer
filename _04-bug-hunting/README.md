# Bug Hunting Exercise

## Description
This project involves debugging Java-based HTTP servers with varying levels of difficulty, each designed with intentional errors to challenge your debugging skills. The servers support basic HTTP functionality and are meant to help you practice identifying and fixing bugs in real-world scenarios.

### Applications
1. `SimpleHttpServer` (Level 1): Basic server with very simple bugs.
2. `MediumHttpServer` (Level 2): Intermediate server with medium difficulty bugs.
3. `SpringBootHttpServer` (Level 3): Spring Boot application with hard-to-find bugs.

## Getting Started
1. Clone the repository:
   ```bash
   git clone https://example.com/java-http-servers.git
   cd java-http-servers
   ```
2. Start the server using your IDE or from the terminal.

## Applications and Bugs

### Level 1: SimpleHttpServer
**File:** `src/main/java/org/example/level1/SimpleHttpServer.java`

#### Steps to Reproduce:
1. Start the server:
   ```bash
   javac SimpleHttpServer.java
   java SimpleHttpServer
   ```
2. Send a GET request to the server:
   ```bash
   curl http://localhost:8000
   ```

#### Actual Results:
- Output stream is not closed, causing resource leaks.
- Client socket is not closed, causing resource leaks.

#### Expected Results:
- Output stream should be properly closed.
- Client socket should be properly closed after handling requests.

### Level 2: MediumHttpServer
**File:** `src/main/java/org/example/level2/MediumHttpServer.java`

#### Steps to Reproduce:
1. Start the server:
   ```bash
   javac RestfulHttpServer.java
   java MediumHttpServer
   ```
2. Send a GET request to the server:
   ```bash
   curl http://localhost:8001
   ```

#### Actual Results:
- Possible NPE during client request handling  
- Improper exception handling leading to insufficient error logging.
- Closing socket without closing streams, leading to potential resource leaks.

#### Expected Results:
- No NPE is thrown during client request
- Proper exception handling with detailed error logging.
- Proper closing of streams before closing the socket.

### Reproducers 
1-2. Look what happen if you do `curl -v telnet://localhost:8001`

### Level 3: SpringBootHttpServer
**File:** `src/main/java/org/example/level3/DemoApplication.java`

#### Steps to Reproduce:
1. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
2. Send requests to the server:
   - Add a product:
     ```bash
     curl -X POST http://localhost:8080/products -d "name=Widget&price=25"
     ```
   - List products:
     ```bash
     curl http://localhost:8080/products
     ```
   - Delete a product:
     ```bash
     curl -X DELETE http://localhost:8080/products/1
     ```

#### Actual Results:
- XSS (i.e., Cross-site scripting) vulnerability due to improper handling of user input.
- Performance issue due to deliberate delay.
- Lack of input validation, leading to potential `NumberFormatException`.

#### Expected Results:
- Proper handling of user input to prevent XSS.
- Efficient request handling without unnecessary delays.
- Proper input validation to handle invalid inputs gracefully.

### Reproducer

1. Visit web-browser on `"http://localhost:8080/hello?name=<script>alert('XSS')</script>"`
2.
```
curl localhost:8080/slow
```
3.
```
curl -X GET localhost:8080/data\?id=dsad
```

## Useful Information

- New line in HTTP headers: `\r\n`
- End of HTTP headers and start of the body: `\r\n\r\n`

## Auxiliary Commands

### SimpleHttpServer
```bash
curl http://localhost:8000
```

### MediumHttpServer
```bash
curl http://localhost:8001
```

### SpringBootHttpServer
- Add a product:
  ```bash
  curl -X POST http://localhost:8080/products -d "name=Widget&price=25"
  ```
- List products:
  ```bash
  curl http://localhost:8080/products
  ```
- Delete a product:
  ```bash
  curl -X DELETE http://localhost:8080/products/1
  ```
