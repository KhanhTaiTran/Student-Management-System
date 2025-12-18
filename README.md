# Student Management System

A comprehensive web-based application for managing school activities, built with **Spring Boot**, **MySQL**, and **JWT Authentication**. The system is designed with a role-based architecture supporting **Admin**, **Teacher**, and **Student** roles.

## 👥 Team Members

| No. | Full Name             | Student ID | Role & Responsibilities                  |
|-----|-----------------------|------------|------------------------------------------|
| 1   | Tran Khanh Tai        | ITITIU21300| Admin Features, Security & Authentication|
| 2   | Nguyen Dy Nien        | ITITIU21272| Teacher Features (Grading, Quiz, Attendance)|
| 3   | La Van Phu            | ITITIU21282| Student Features (Payment, Schedule, Results)|

---

## 🚀 Tech Stack

- **Backend:** Java 17, Spring Boot 3.x
- **Database:** MySQL 8.0
- **Security:** Spring Security, JWT (JSON Web Token)
- **Frontend:** Thymeleaf (Server-side rendering), HTML5, CSS3, Bootstrap 5
- **Build Tool:** Maven

---

## 📂 Project Structure

```text
src/main/java/com/example/studentmanagementsystem
│
├── config                 # Application configurations (Swagger, AppConfig...)
├── controller             # API Controllers (Handling HTTP requests)
├── dto                    # Data Transfer Objects (Request/Response bodies)
├── entity                 # JPA Entities (Database tables mapping)
├── exception              # Global Exception Handling
├── repository             # Data Access Layer (Spring Data JPA)
├── security               # Security configurations (JWT Filter, Provider)
├── service                # Business Logic Layer
│   └── impl               # Service Implementations
└── utils                  # Utility classes (DataSeeder, Constants...)

src/main/resources
├── static                 # Static resources (CSS, JS, Images)
├── templates              # Thymeleaf Views (HTML files)
│   ├── admin              # Admin dashboard & pages
│   ├── teacher            # Teacher dashboard & pages
│   ├── student            # Student dashboard & pages
│   └── fragments          # Reusable UI components (Header, Footer)
└── application.properties # Database & Application properties
└── messase.properties     # Use to change language between VietNam and English
└── message-vi.properties
```

---

## 🛠️ Installation & Setup Guide
1. Prerequisites
Ensure you have the following installed on your machine:

- Java Development Kit (JDK) 17 or higher.

- Maven (Apache Maven).

- MySQL Server.

2. Database Configuration
  - Create a new database in MySQL:
      ```sql
      CREATE DATABASE student_management_system;
      ```
  - Open src/main/resources/application.properties and update your MySQL credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/student_management_system?useSSL=false&allowPublicKeyRetrieval=true
    spring.datasource.username=root
    spring.datasource.password=YOUR_PASSWORD
    ```
3. Build the Project
    Open your terminal in the project root directory and run:
    ```bash
    mvn clean install
    ```
4. Run the Application
   ```bash
   mvn spring-boot:run
   ```
   Alternatively, you can run the StudentManagementSystemApplication.java file directly from your IDE.

   The application will start at: http://localhost:8080

---

## 🔐 Default Credentials
On the first run, the system automatically seeds a default Admin account.

- Username: admin

- Password: admin123

API Testing (via Postman)
To get the Access Token:

- Endpoint: POST http://localhost:8080/api/auth/login

- Body (JSON):
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```

---

## 🌟 Key Features
### 👨‍💻 Admin
- User Management: Create, update, and manage accounts for Teachers and Students.

- Role Management: Assign roles and permissions.

- System Announcements: Post notifications to all users.

### 👩‍🏫 Teacher
- Class Management: View assigned classes and student lists.

- Grading System: Input and update student grades.

- Quiz Management: Create quizzes and track results.

- Attendance: Check and record student attendance.

### 👨‍🎓 Student
- Dashboard: View personal schedule and notifications.

- Academic Results: Check grades and attendance status.

- Tuition & Payment: View tuition fees and perform transactions (Deposit/Payment simulation).
