### Tasks for Creating a Basic Web Server Using Spring Boot

#### Task 1: Expand the Controller
- Add a new endpoint `/api/users` that accepts both GET and POST requests.
    - GET should return a list of users.
    - POST should accept a new user's details and add it to the list.
- Implement the user storage with a simple ArrayList.

#### Task 2: Implement Data Validation
- Add validation to the `/data` endpoint to ensure that the data received is not null or empty.
- Return a `400 Bad Request` if the validation fails.

#### Task 3: Secure an Endpoint
- Secure the `/about` endpoint so that it requires an API key passed as a query parameter.
- Only allow access if the API key matches a predefined string.

#### Task 4: Create a Custom Error Page
- Customize the error response for the web server.
- Implement a global exception handler that returns a custom HTML page whenever an error occurs.

#### Task 5: Deployment and Testing
- Deploy the Spring Boot application locally and test all endpoints.
- Use `curl` commands to interact with your server and validate the responses.
- Document the results and prepare to discuss what modifications could be made to transition the server to a production environment.

### Submission Requirements
- For both tasks, submit all source code via a version control repository link (e.g., GitHub).
- Include a README.md with setup instructions, endpoint descriptions, and notes on design decisions.
- Provide screenshots or logs demonstrating successful responses and error handling.

These tasks encourage hands-on learning, helping students deepen their understanding of web server development and the practical use of frameworks like Spring Boot in real-world applications.