# Bug Hunting Exercise

## Description
This project involves debugging Java-based HTTP servers with varying levels of difficulty, each designed with intentional errors to challenge your debugging skills. 
The servers support basic HTTP functionality and are meant to help you practice identifying and fixing bugs in real-world scenarios.

### Applications
1. `SimpleHttpServer` (Level 1): A basic HTTP server with straightforward, beginner-level bugs.
2. `RestfulHttpServer` (Level 2): A small REST server with more realistic and tricky issues.
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

#### Actual Results (Intentional Bugs):
- ?
- ?
- ?

#### Expected Results:
- Full HTTP response should be sent to the client.
- Requested paths are correctly extracted, and the right page is shown.
- /about should show the About page, not 404.


### Level 2: MediumHttpServer
**File:** `src/main/java/org/example/level2/MediumHttpServer.java`

#### Steps to Reproduce:
1. Start the server:
   ```bash
   javac RestfulHttpServer.java
   java RestfulHttpServer
   ```
2. Try requests:
    ```bash
    curl -X POST http://localhost:8080/items
    curl http://localhost:8080/items
    curl http://localhost:8080/items/1
    curl -X DELETE http://localhost:8080/items/1
    ```
     
#### Actual Results:
- ?
- ?
- ?

#### Expected Results:
- Each HTTP method and path should trigger the correct handler.
- Items should be created, listed, and deleted with consistent IDs.
- 404 Not Found status should be sent when an item or route does not exist.
- JSON should be valid and parseable

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
