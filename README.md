# 🎓 Student Task Tracker

A web application built with Spring Boot, Thymeleaf, and MySQL that allows admins to assign tasks to students and track their progress.

## ✅ Key Features

- **Admin**
  - Login, assign/edit/delete tasks
  - View tasks by status (To Do / In Progress / Completed)
  - See only tasks assigned by them

- **Student**
  - Login and view assigned tasks
  - Update task status
  - View task details
  - Change password

- **Common**
  - Real-time validation with AJAX
  - Data persisted in MySQL
  - Clean UI with Thymeleaf and Bootstrap

## 🧰 Tech Stack

- Spring Boot + Spring Data JPA  
- Thymeleaf + Bootstrap  
- MySQL + JavaScript (AJAX)  

## 🚀 How to Run

1. Clone the repo  
2. Configure `application.properties` with your DB credentials  
3. Run using:  
   ```bash
   ./mvnw spring-boot:run
