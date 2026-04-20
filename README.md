🎓 Course Enrollment System (Java)


📌 Overview
---

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

## 📸 Screenshots

<table>
  <tr>
    <td align="center"><b>Login Screen</b></td>
    <td align="center"><b>Admin Dashboard</b></td>
    <td align="center"><b>Student Enrollment</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/a1a93d4c-67e0-4dd4-bba0-0acab2a66985" width="300"/></td>
    <td><img src="https://github.com/user-attachments/assets/27b7fb49-5531-47f0-aa73-3cdf3ac831d7" width="300"/></td>
    <td><img src="https://github.com/user-attachments/assets/25580d30-f959-44ab-b87a-106ddd408ead" width="300"/></td>
  </tr>
</table>

