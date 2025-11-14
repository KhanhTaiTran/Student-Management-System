-- Create Database
CREATE DATABASE IF NOT EXISTS student_management_system;
USE student_management_system;

-- Drop tables if exist (for clean setup)
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS grades;
DROP TABLE IF EXISTS enrollment;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS users;

-- Users table (base table for authentication)
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    user_type ENUM('student', 'teacher', 'admin') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Students table
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    gender ENUM('Male', 'Female', 'Other'),
    phone VARCHAR(20),
    address TEXT,
    enrollment_date DATE NOT NULL,
    major VARCHAR(100),
    year_level INT DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Teachers table
CREATE TABLE teachers (
    teacher_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    gender ENUM('Male', 'Female', 'Other'),
    phone VARCHAR(20),
    address TEXT,
    department VARCHAR(100),
    hire_date DATE NOT NULL,
    specialization VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Courses table
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    description TEXT,
    credits INT NOT NULL,
    teacher_id INT,
    semester VARCHAR(20),
    year INT,
    max_students INT DEFAULT 30,
    schedule VARCHAR(100),
    room VARCHAR(50),
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id) ON DELETE SET NULL
);

-- Enrollment table (many-to-many relationship between students and courses)
CREATE TABLE enrollment (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE NOT NULL,
    status ENUM('enrolled', 'dropped', 'completed') DEFAULT 'enrolled',
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    UNIQUE KEY unique_enrollment (student_id, course_id)
);

-- Grades table
CREATE TABLE grades (
    grade_id INT PRIMARY KEY AUTO_INCREMENT,
    enrollment_id INT NOT NULL,
    assignment_name VARCHAR(100),
    assignment_type ENUM('homework', 'quiz', 'midterm', 'final', 'project') NOT NULL,
    score DECIMAL(5,2),
    max_score DECIMAL(5,2),
    percentage DECIMAL(5,2),
    letter_grade VARCHAR(2),
    grade_date DATE,
    comments TEXT,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(enrollment_id) ON DELETE CASCADE
);

-- Attendance table
CREATE TABLE attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    enrollment_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('present', 'absent', 'late', 'excused') NOT NULL,
    notes TEXT,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(enrollment_id) ON DELETE CASCADE,
    UNIQUE KEY unique_attendance (enrollment_id, attendance_date)
);

-- Create indexes for better performance
CREATE INDEX idx_students_user_id ON students(user_id);
CREATE INDEX idx_teachers_user_id ON teachers(user_id);
CREATE INDEX idx_enrollment_student ON enrollment(student_id);
CREATE INDEX idx_enrollment_course ON enrollment(course_id);
CREATE INDEX idx_grades_enrollment ON grades(enrollment_id);
CREATE INDEX idx_attendance_enrollment ON attendance(enrollment_id);
CREATE INDEX idx_attendance_date ON attendance(attendance_date);

-- Insert sample admin user
INSERT INTO users (username, password, email, user_type) 
VALUES ('admin', 'admin123', 'admin@school.com', 'admin');

-- Insert sample teacher
INSERT INTO users (username, password, email, user_type) 
VALUES ('teacher1', 'teacher123', 'teacher1@school.com', 'teacher');

INSERT INTO teachers (user_id, first_name, last_name, date_of_birth, gender, phone, department, hire_date, specialization)
VALUES (2, 'John', 'Smith', '1980-05-15', 'Male', '0123456789', 'Computer Science', '2020-01-15', 'Software Engineering');

-- Insert sample student
INSERT INTO users (username, password, email, user_type) 
VALUES ('student1', 'student123', 'student1@school.com', 'student');

INSERT INTO students (user_id, first_name, last_name, date_of_birth, gender, phone, enrollment_date, major, year_level)
VALUES (3, 'Jane', 'Doe', '2002-08-20', 'Female', '0987654321', '2023-09-01', 'Computer Science', 1);

-- Insert sample courses
INSERT INTO courses (course_code, course_name, description, credits, teacher_id, semester, year, schedule, room)
VALUES 
('CS101', 'Introduction to Programming', 'Basic programming concepts using Java', 3, 1, 'Fall', 2024, 'Mon-Wed 9:00-10:30', 'Room 101'),
('CS102', 'Data Structures', 'Fundamental data structures and algorithms', 4, 1, 'Fall', 2024, 'Tue-Thu 13:00-15:00', 'Room 102'),
('MATH101', 'Calculus I', 'Differential and integral calculus', 4, NULL, 'Fall', 2024, 'Mon-Wed-Fri 10:00-11:00', 'Room 201');

-- Insert sample enrollment
INSERT INTO enrollment (student_id, course_id, enrollment_date, status)
VALUES (1, 1, '2024-09-01', 'enrolled'),
       (1, 2, '2024-09-01', 'enrolled');

-- Insert sample grades
INSERT INTO grades (enrollment_id, assignment_name, assignment_type, score, max_score, percentage, letter_grade, grade_date)
VALUES 
(1, 'Homework 1', 'homework', 85, 100, 85.00, 'B', '2024-09-15'),
(1, 'Midterm Exam', 'midterm', 78, 100, 78.00, 'C', '2024-10-20'),
(2, 'Quiz 1', 'quiz', 92, 100, 92.00, 'A', '2024-09-10');

-- Insert sample attendance
INSERT INTO attendance (enrollment_id, attendance_date, status)
VALUES 
(1, '2024-09-02', 'present'),
(1, '2024-09-04', 'present'),
(1, '2024-09-06', 'late'),
(2, '2024-09-03', 'present'),
(2, '2024-09-05', 'absent');