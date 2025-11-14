
# Student Management System

A comprehensive web-based Student Management System built with Java EE, providing streamlined management of students, courses, grades, attendance, and academic records.

## 📋 Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Database Configuration](#database-configuration)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Usage Guide](#usage-guide)
- [Debugging](#debugging)
- [Troubleshooting](#troubleshooting)

## ✨ Features

### User Management
- **User Authentication**: Secure sign-in and sign-up functionality
- **Password Recovery**: Forgot password feature with email-based reset
- **Role-Based Access**: Support for Students, Teachers, and Admins

### Academic Management
- **Student Management**: Track student information, enrollment, and academic progress
- **Course Management**: Create and manage courses with schedules and teacher assignments
- **Grade Tracking**: Record and monitor student grades across different assignment types
- **Attendance System**: Track daily attendance with status tracking (present, absent, late, excused)
- **Enrollment Management**: Handle course enrollments and student registrations

### User Interface
- **Modern Design**: Beautiful, responsive UI with gradient backgrounds and smooth animations
- **Mobile-Friendly**: Fully responsive design that works on desktop, tablet, and mobile
- **Interactive Elements**: Parallax effects, hover animations, and smooth transitions

## 🛠 Technology Stack

### Backend
- **Java EE 8**: Core application framework
- **Servlets & JSP**: Web layer implementation
- **JDBC**: Database connectivity
- **Maven**: Dependency management and build tool

### Frontend
- **HTML5 & CSS3**: Modern web standards
- **JavaScript**: Interactive UI components
- **Bootstrap 4**: Responsive grid and components
- **JSTL**: JSP Standard Tag Library

### Database
- **MySQL 8.0+**: Primary data storage
- **JDBC Driver**: MySQL Connector/J

### Server
- **Apache Tomcat 9.0+**: Application server

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 8 or newer
  - Download: https://www.oracle.com/java/technologies/downloads/
  - Set `JAVA_HOME` environment variable
- **Apache Maven**: Version 3.6+
  - Download: https://maven.apache.org/download.cgi
  - Add to PATH
- **MySQL Server**: Version 8.0+
  - Download: https://dev.mysql.com/downloads/mysql/
- **Apache Tomcat**: Version 9.0+
  - Download: https://tomcat.apache.org/download-90.cgi
- **VS Code** (recommended): With Java extensions
  - Extensions: `Language Support for Java`, `Debugger for Java`, `Maven for Java`

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/KhanhTaiTran/Student-Management-System.git
cd StudentManagementSystem
```

### 2. Database Setup

1. **Start MySQL Server** and log in:
   ```bash
   mysql -u root -p
   ```

2. **Create Database and Tables**: Run the SQL script located in the project root:
   ```bash
   mysql -u root -p < "MySQL Script.sql"
   ```
   
   Or manually execute the script in MySQL Workbench.

3. **Verify Database**: The script creates:
   - Database: `student_management_system`
   - Tables: `users`, `students`, `teachers`, `courses`, `enrollment`, `grades`, `attendance`
   - Sample data for testing

### 3. Configure Database Connection

Update the database credentials in `src/main/java/com/mycompany/studentmanagementsystem/db/DBUtil.java`:

```java
private static final String DEFAULT_PASSWORD = "your_mysql_password";
```

Or set environment variables:
```bash
set DB_URL=jdbc:mysql://localhost:3306/student_management_system?useSSL=false&serverTimezone=UTC
set DB_USER=root
set DB_PASSWORD=your_password
```

### 4. Build the Project

```bash
mvn clean package
```

The WAR file will be generated in `target/StudentManagementSystem.war`.

## 🗄 Database Configuration

### Default Connection Settings
- **Host**: 127.0.0.1
- **Port**: 3306
- **Database**: student_management_system
- **User**: root
- **Password**: admin (change this!)

### Sample Users (from SQL script)
| Username | Password | Role | Email |
|----------|----------|------|-------|
| admin | admin123 | Admin | admin@school.com |
| teacher1 | teacher123 | Teacher | teacher1@school.com |
| student1 | student123 | Student | student1@school.com |

## 🏃 Running the Application

### Method 1: Manual Deployment (Recommended)

1. **Deploy WAR to Tomcat**:
   ```bash
   copy target\StudentManagementSystem.war %TOMCAT_HOME%\webapps\
   ```

2. **Start Tomcat**:
   ```bash
   %TOMCAT_HOME%\bin\startup.bat
   ```

3. **Access the Application**:
   Open browser: `http://localhost:8080/StudentManagementSystem/`

### Method 2: VS Code Tomcat Extension

1. Install "Tomcat" extension in VS Code
2. Add your Tomcat installation to the extension
3. Right-click the WAR file → Deploy to Tomcat
4. Start server from VS Code

### Method 3: Direct Run (Development)

For quick testing during development:
```bash
mvn tomcat7:run
```

## 📁 Project Structure

```
StudentManagementSystem/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/mycompany/studentmanagementsystem/
│       │       ├── LoginProcessServlet.java
│       │       ├── SignupServlet.java
│       │       ├── ForgotPasswordServlet.java
│       │       ├── JAXRSConfiguration.java
│       │       ├── db/
│       │       │   └── DBUtil.java
│       │       ├── dao/
│       │       │   └── UserDAO.java
│       │       └── resources/
│       │           └── JavaEE8Resource.java
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml
│       └── webapp/
│           ├── index.html (Welcome page)
│           ├── WEB-INF/
│           │   ├── web.xml
│           │   ├── beans.xml
│           │   └── jsp/
│           │       ├── login_page.jsp
│           │       ├── signup.jsp
│           │       └── forgot_password.jsp
│           ├── css/
│           ├── js/
│           └── img/
├── target/ (generated)
├── pom.xml
├── MySQL Script.sql
└── README.md
```

## 📖 Usage Guide

### For Students
1. **Sign Up**: Create a new account with username, email, and password
2. **Sign In**: Access your dashboard with credentials
3. **View Courses**: See enrolled courses and schedules
4. **Check Grades**: Monitor performance across assignments
5. **Track Attendance**: View attendance records

### For Teachers
1. **Sign In**: Use teacher credentials
2. **Manage Courses**: Create and update course information
3. **Record Grades**: Enter student grades for assignments
4. **Mark Attendance**: Track daily student attendance
5. **Generate Reports**: View class performance analytics

### For Administrators
1. **User Management**: Create and manage user accounts
2. **System Configuration**: Configure system-wide settings
3. **Data Management**: Manage all academic data
4. **Reports**: Generate comprehensive reports

## 🐛 Debugging

### Enable Debug Mode

1. **Start Tomcat in Debug Mode**:
   ```bash
   %TOMCAT_HOME%\bin\catalina.bat jpda start
   ```

2. **Create VS Code `launch.json`**:
   ```json
   {
     "type": "java",
     "name": "Attach to Tomcat",
     "request": "attach",
     "hostName": "localhost",
     "port": 8000
   }
   ```

3. **Set Breakpoints** in your Java code and attach the debugger

### View Logs

Check Tomcat logs for errors:
```bash
type %TOMCAT_HOME%\logs\catalina.YYYY-MM-DD.log
type %TOMCAT_HOME%\logs\localhost.YYYY-MM-DD.log
```

## ❗ Troubleshooting

### Build Issues

**Problem**: Maven build fails
```bash
# Solution: Clean and rebuild
mvn clean install -U
```

**Problem**: Dependency download errors
```bash
# Solution: Force update
mvn dependency:purge-local-repository
mvn clean package
```

### Database Issues

**Problem**: "No suitable driver found"
- **Solution**: The MySQL driver is now auto-loaded in `DBUtil.java`
- Verify `mysql-connector-j` is in `pom.xml`

**Problem**: "Access denied for user 'root'"
- **Solution**: Update password in `DBUtil.java` or set `DB_PASSWORD` environment variable

**Problem**: "Unknown database 'student_management_system'"
- **Solution**: Run the `MySQL Script.sql` to create the database

### Deployment Issues

**Problem**: 404 Error - Servlet not found
- **Solution**: Servlets are now explicitly mapped in `web.xml`
- Rebuild: `mvn clean package`
- Redeploy the WAR

**Problem**: Port 8080 already in use
```bash
# Find and kill process using port 8080
netstat -ano | findstr :8080
taskkill /PID <process_id> /F
```

**Problem**: Tomcat won't start
- Check `JAVA_HOME` is set correctly
- Verify no other Tomcat instance is running
- Check logs in `%TOMCAT_HOME%\logs\`

### Runtime Issues

**Problem**: JSP not found
- Verify JSP files are in `src/main/webapp/WEB-INF/jsp/`
- Check servlet forwards use correct path: `/WEB-INF/jsp/login_page.jsp`

**Problem**: CSS/JS not loading
- Static resources should be in `src/main/webapp/` (not in `WEB-INF`)
- Clear browser cache

## 🔧 Helpful Commands

```bash
# Build
mvn clean package

# Build without tests
mvn clean package -DskipTests

# Run tests
mvn test

# Check dependencies
mvn dependency:tree

# Start Tomcat
%TOMCAT_HOME%\bin\startup.bat

# Stop Tomcat
%TOMCAT_HOME%\bin\shutdown.bat

# View Tomcat logs (tail)
powershell Get-Content %TOMCAT_HOME%\logs\catalina.*.log -Tail 50
```

## 📝 Configuration Files

### `pom.xml` - Maven Configuration
- Dependencies: Java EE API, MySQL Connector, JSTL
- Plugins: Maven Compiler (3.8.1), Maven WAR (3.3.2)
- Build settings and project metadata

### `web.xml` - Web Application Descriptor
- Servlet mappings for `/login`, `/signup`, `/forgot-password`
- Session configuration
- Welcome files

### `DBUtil.java` - Database Configuration
- JDBC connection management
- Default connection settings
- Environment variable support

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/YourFeature`
3. Commit changes: `git commit -m 'Add YourFeature'`
4. Push to branch: `git push origin feature/YourFeature`
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **KhanhTaiTran** - *Initial work* - [GitHub](https://github.com/KhanhTaiTran)

## 🙏 Acknowledgments

- Bootstrap team for the responsive framework
- Apache Tomcat community
- MySQL development team
- VS Code and Java extensions contributors

## 📞 Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Contact: [tktai2003@gmail.com]