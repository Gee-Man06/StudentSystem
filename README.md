🎓 Course Enrollment System (Java)

📌 Overview

The Course Enrollment System is a Java-based application developed as a group project to manage student course registrations.
The system provides role-based functionality where an administrator manages students and modules, and students can securely 
enroll in available courses.

🚀 Features
---

👨‍💼 Admin
---

- Add new students to the system
- Remove existing students
- Create and manage course modules
- Maintain system data integrity

---

👨‍🎓 Student
---

- Secure login authentication (no self-registration)
- View all available modules
- Enroll in modules
- Unenroll from modules
- Prevented from enrolling in the same module more than once

---

🛠️ Tech Stack
---
- Language: Java
- Database: Apache Derby
- Architecture: Object-Oriented Design
- Tools:  NetBeans 

---
🧠 System Design
---

- Role-based access control (Admin vs Student)
- Many-to-many relationship between Students and Modules
- Enrollment validation to prevent duplicate registrations
- Structured using core OOP principles (encapsulation, modular design)

---

⚠️ Challenges & Solutions
---
- Challenge: Preventing duplicate module enrollments
    - Solution: Implemented validation checks before inserting enrollment records into the database
- Challenge: Managing relationships between students and modules
    - Solution: Used an intermediary enrollment structure to handle many-to-many relationships

⚙️ How to Run the Project (Setup Guide)
---
📥 Prerequisites
---

Make sure you have the following installed:
- Java JDK (8 or higher)
- NetBeans IDE
- Apache Derby (usually bundled with NetBeans)

🧾 Steps to Run
---

- Clone the Repository
- git clone https:(https://github.com/Gee-Man06/StudentSystem)

## Open Project in NetBeans
---

- Launch NetBeans
- Click File → Open Project
- Navigate to the cloned project folder and open it
- Set Up the Database (Apache Derby)
- Open the Services tab in NetBeans
- Expand Databases → Java DB (Derby)
- Start the Derby server
- Create a new database (if not already included)
- Configure Database Connection
- Update database connection settings in the project (if needed)
- Ensure username, password, and database name match your setup

## Run the Project
---

- Right-click the project → Run
- The application should start

📸 Screenshots

Add screenshots of your system here (optional but recommended)
