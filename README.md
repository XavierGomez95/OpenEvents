# 📱 OpenEvents

**OpenEvents** is an Android application developed as the final project for the **Mobile Devices Development I** subject.  
The app allows users to **manage events, add friends, and create personal events**, with all information handled through an **API**.

---

## ✨ Features

- **Authentication**
  - User registration and login
  - Token-based session handling (persistent login unless the user explicitly logs out)

- **Bottom Navigation Menu**
  - Navigation across **Events**, **Profile**, and **Friends search**

- **Events**
  - View events created by all registered users
  - Filter events by category and other criteria
  - Manage personal events (create, edit, delete)

- **Friends**
  - Search for users by email, first name, or last name
  - Send and accept friend requests
  - View the current friends list

- **Profile**
  - Edit user information (except password, due to API limitations)
  - Access to friend management

---

## 🏗️ Architecture

The codebase follows a layered organization inspired by **MVC (Model-View-Controller)**:

- **View**
  - Divided into:
    - `activities`
    - `fragments`
    - `adapters`
  - Handles UI and user interaction

- **Business**
  - Contains classes for processing and managing data

- **Model**
  - Represents core entities (users, events, friends, etc.)

- **Persistence**
  - Contains classes and interfaces responsible for API communication
  - Example: `OpenEventsAPI` interface with methods to manage users and events

---

## 🛠️ Technologies & Tools

- **Language**: Java (Android)
- **IDE**: Android Studio
- **API**: REST-based API for users and events
- **Version Control**: Git (local) & GitHub (remote)
- **Project Management**: Trello (Kanban methodology)
- **Development Methodology**: Pair Programming (for solving complex tasks)

---

## 👥 Authors

- [Iris Querol](https://github.com/irisquerol)
- [Xavier Gomez](https://github.com/XavierGomez95)
