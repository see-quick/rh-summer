# Bug Hunting

## Description
This project focuses on identifying and fixing bugs in a given HTTP server implementation. It aims to improve debugging and problem-solving skills.

The Java program 'SimpleHttpServer.java' creates a basic HTTP server that can handle GET, POST, PUT, and DELETE requests for managing products. However, the program has several issues, including incorrect server port initialization, a typo in the HTTP method handling, improper ID incrementation, and mishandling of character encoding and HTTP status codes.

### == Steps to Reproduce ==

 1. Start the server.
 2. Make a POST request to add a new product with name and price.
 3. Make a PUT request to update the product's name and price.

### == Actual results ==

 - The server is incorrectly initialized with a string instead of a port number, causing a failure to start.
 - Incorrect handling of the 'POST' method as 'POOST', causing method not allowed errors.
 - The ID increments incorrectly after adding a product, potentially leading to skipped IDs.
 - Incorrect HTTP status codes are returned for successful deletions.

### == Expected results ==

 - Correct initialization of the server on a valid port.
 - Proper handling of the POST method.
 - Product IDs should increment correctly without skipping.
 - Appropriate HTTP status codes returned for CRUD operations.

## Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/see-quick/rh-summer.git
   cd rh-summer/_04-bug-hunting
2. Start the server (either in IDE or terminal)

## Useful things

- New line in HTTP headers. (`\r\n`)
- End of HTTP headers and start of the body. (``\r\n\r\n``)

## Auxiliary commands

```bash
curl localhost:8000/products
[]
```

```bash
curl -X POST localhost:8000/products\&name=ball\&price=10
Product added
```

```bash
curl localhost:8000/products
[Product 2: ball, Price: 10]
```

```bash
curl -X PUT -H "Content-Length: 18" -d "name=ball&price=24" http://localhost:8000/products/1
```