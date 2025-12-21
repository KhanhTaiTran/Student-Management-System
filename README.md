# Student Management System (SMS)

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![Maven](https://img.shields.io/badge/Build-Maven-red)
![License](https://img.shields.io/badge/License-Apache%202.0-lightgrey)

A comprehensive, role-based web application for managing school activities. Built with Spring Boot, Spring Security, and JWT, this system provides a modern platform for connecting administrators, teachers, and students.

---

## Table of Contents

- [✨ Key Features](#-key-features)
- [🛠️ Tech Stack](#️-tech-stack)
- [📂 Project Structure](#-project-structure)
- [🚀 Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [▶️ Usage](#️-usage)
  - [Running the Application](#running-the-application)
  - [Default Credentials](#default-credentials)
- [🤝 Authors](#-authors)
- [📄 License](#-license)

---

## ✨ Key Features

The system supports three distinct user roles, each with a dedicated set of functionalities.

### 👨‍💻 Administrator

- **User Management:** Create, update, and manage accounts for teachers and students.
- **Course & Class Management:** Define courses, schedule classes, and manage academic semesters.
- **Teacher Assignment:** Assign teachers to specific classes.
- **System Overview:** View dashboard with key statistics and user activity.

### 👩‍🏫 Teacher

- **Class & Schedule Management:** View assigned classes and teaching schedules.
- **Grading System:** Input and update student grades for assignments, midterms, and finals.
- **Student Management:** View class rosters and student information.

### 👨‍🎓 Student

- **Personal Dashboard:** View academic overview, notifications, and daily schedule.
- **Course Registration:** Browse and register for available courses each semester.
- **Academic Results:** Check grades, GPA, and academic progress.
- **Weekly Schedule:** View a detailed weekly class schedule.
- **Tuition & Payment:** View tuition fee status and simulate payments.

---

## 🛠️ Tech Stack

| Category       | Technology                                                  |
| -------------- | ----------------------------------------------------------- |
| **Backend**    | Java 17, Spring Boot 3.x, Spring Security, Spring Data JPA  |
| **Database**   | MySQL 8.0                                                   |
| **Security**   | JWT (JSON Web Token) for stateless authentication           |
| **Frontend**   | Thymeleaf (Server-Side Rendering), HTML5, CSS3, Bootstrap 5 |
| **Build Tool** | Maven                                                       |

---

## � Project Structure

The project follows a standard Maven layout and is organized by feature and layer for maintainability.

```text
src/main/java/com/example/studentmanagementsystem
│
├── config          # Application-wide configurations (Security, Beans)
├── controller      # API Controllers (Handling HTTP requests)
├── dto             # Data Transfer Objects (Request/Response models)
├── entity          # JPA Entities (Database table mappings)
├── exception       # Global exception handling
├── repository      # Data Access Layer (Spring Data JPA interfaces)
├── security        # Security configurations (JWT Filter, Provider, etc.)
├── service         # Business logic layer
│   └── impl        # Service implementations
└── utils           # Utility classes (e.g., DataSeeder)

src/main/resources
├── static          # Static assets (CSS, JS, Images)
├── templates       # Thymeleaf views (HTML files)
│   ├── admin       # Admin-specific pages
│   ├── teacher     # Teacher-specific pages
│   ├── student     # Student-specific pages
│   └── fragments   # Reusable UI components (Header, Footer, etc.)
├── application.properties # Main application configuration
├── messages.properties    # I18n message bundles (Default/English)
└── messages_vi.properties # I18n message bundles (Vietnamese)
```

---

## 🚀 Getting Started

Follow these instructions to get a local copy of the project up and running.

### Prerequisites

Ensure you have the following installed on your machine:

- Java Development Kit (JDK) 17 or higher
- Apache Maven
- MySQL Server 8.0+

### Installation

1.  **Clone the repository**

    ```sh
    git clone https://github.com/KhanhTaiTran/Student-Management-System.git
    cd student-management-system
    ```

2.  **Create the MySQL Database**

    ```sql
    CREATE DATABASE student_management_system;
    ```

3.  **Build the Project**
    This command will download dependencies and compile the source code.
    ```bash
    mvn clean install
    ```

### Configuration

This project uses environment variables to handle sensitive credentials. You will need to set the following variables on your system:

- `DB_PASSWORD`: Your MySQL database password.
- `GMAIL_APP_PASSWORD`: A Google Mail App Password for the email sending feature.

The application reads these values from `src/main/resources/application.properties`.

> **Note on Email Setup:** For instructions on how to generate the `GMAIL_APP_PASSWORD`, please refer to the `DOCUMENT.md` file in the project root.

---

## ▶️ Usage

### Running the Application

You can run the application using the Spring Boot Maven plugin:

```bash
mvn spring-boot:run
```

Alternatively, you can run the `StudentManagementSystemApplication.java` file directly from your IDE.

The application will be accessible at **http://localhost:8080**.

### Default Credentials

On the first run, the system automatically seeds a default administrator account in the database. You can use these credentials to log in through the web interface or to obtain a JWT token via the API.

- **Username:** `admin`
- **Password:** `admin123`

#### API Authentication Example

To get a JWT Access Token, send a POST request to the login endpoint.

- **Endpoint:** `POST http://localhost:8080/api/auth/login`
- **Body (JSON):**
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```

---

## 🤝 Authors

| No. | Full Name      | Student ID  | Role & Responsibilities                                   | Distribution |
| --- | -------------- | ----------- | --------------------------------------------------------- | ------------ |
| 1   | Tran Khanh Tai | ITITIU21300 | Admin Features, Security & Authentication, Back-end logic | 40%          |
| 2   | Nguyen Dy Nien | ITITIU21272 | Teacher Features (Grading, Quiz, Attendance)              | 30%          |
| 3   | La Van Phu     | ITITIU21282 | Student Features (Payment, Schedule, Results)             | 30%          |

---

## 📄 License

This project is licensed under the Apache License 2.0. See the `LICENSE` file for more details.
