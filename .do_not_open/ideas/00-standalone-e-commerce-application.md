# Session 1: Building the Standalone Application

## Objective:
Develop a simple yet functional e-commerce inventory management system.

## Tools and Technologies:
- **Backend Framework:** Choose Flask for Python due to its simplicity and flexibility, or Express for Node.js if JavaScript is preferred.
- **Database:** MySQL or PostgreSQL for relational databases, or MongoDB for a NoSQL option, depending on the project requirements.
- **Development Environment:** Ensure you have Python/Node.js, the chosen database, and a text editor or IDE (like VS Code) set up.

## Mini-Lecture:

### 1. System Overview and Requirements:
   - Briefly outline the application’s purpose: to track and manage inventory for an e-commerce site.
   - List the core features: adding new inventory items, updating existing items, deleting items, and viewing the inventory list.

### 2. Database Schema Design:
   - Discuss the importance of a well-designed database schema.
   - Present the proposed schema, which should include tables/collections for inventory items at a minimum. Each item might have an ID, name, description, quantity, and price.

### 3. Introduction to RESTful APIs:
   - Cover the basics of RESTful principles and how they apply to designing an effective API for managing resources (in this case, inventory items).

## Coding Activity:

### 1. Project Setup:
   - Initialize a new project in Flask or Express.
   - Set up the project structure, including folders for models, routes/controllers, and utilities if needed.

### 2. Database Connection:
   - Configure the connection to your chosen database, creating the necessary tables or collections based on your schema design.

### 3. Developing CRUD Operations:
   - **Create:** Implement an endpoint to add new inventory items to the database.
   - **Read:** Develop endpoints to retrieve the list of all inventory items and the details of a single item by ID.
   - **Update:** Add functionality to modify an existing inventory item’s details.
   - **Delete:** Implement an endpoint to remove an inventory item from the database.

## Wrap-Up and Discussion:

- **Review the day's work:** Go over the implemented features and discuss any challenges encountered.
- **Testing the API:** Demonstrate how to test the created API endpoints using tools like Postman or CURL to ensure they work as expected.
- **Homework/Extension:** Encourage exploring additional features, such as categorizing items, searching/filtering the inventory, or implementing authentication for API access.

## Preparation for Next Session:
Briefly introduce what’s coming next: exploring the real-world application and limitations of the standalone system, which will set the stage for learning about containerization, scalability, and eventually deploying to Kubernetes.
