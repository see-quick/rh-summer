Introducing Spring Boot to handle HTTP requests provides an opportunity to explore a more sophisticated framework that simplifies web development with Java. Here’s a structured exercise for students, formatted in Markdown, that guides them through creating a basic web server using Spring Boot to handle different HTTP methods and routes.

```markdown
## Exercise: Create a Basic Web Server Using Spring Boot

### Objective
- Build a simple web server using Spring Boot that handles different types of HTTP requests and routes.

### Instructions

#### Step 1: Set Up Spring Boot
- Start with setting up a new Spring Boot project using [Spring Initializr](https://start.spring.io/).
  - Choose Maven or Gradle as your build tool.
  - Add dependencies for 'Spring Web'.
- Download the generated project and open it in your preferred IDE (Eclipse, IntelliJ IDEA, etc.).

#### Step 2: Create a Controller
- Create a new Java class file in the `src/main/java/{your package}` directory named `WebController`.
- Add the following code to handle different endpoints and HTTP methods:

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the Homepage!";
    }

    @GetMapping("/about")
    public String about() {
        return "About Us";
    }

    @GetMapping("/contact")
    public String contact() {
        return "Contact Us";
    }

    @PostMapping("/data")
    public String postData(String data) {
        return "Data received: " + data;
    }
}
```

#### Step 3: Run Your Application
- Run your Spring Boot application by executing the main application class.
- Spring Boot will start the embedded Tomcat server typically on port 8080.

#### Step 4: Test Each Endpoint
- Use a web browser or the `curl` command to test each of the endpoints:
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
    - For posting data (using POST):
      ```bash
      curl -X POST -d "data=Sample Data" http://localhost:8080/data
      ```

#### Step 5: Add Your Own Endpoint
- Add an additional endpoint `/services` to the `WebController` class that lists the services offered.
- Test the new endpoint using `curl` or a web browser.

### Submission
- Submit the complete source code of your `WebController` and the main application class.
- Provide screenshots or logs showing successful responses from each endpoint.

### Discussion
- Discuss how Spring Boot simplifies the creation of web servers.
- Explore additional features like handling different data formats (JSON, XML) and integrating with other Spring Boot functionalities.
