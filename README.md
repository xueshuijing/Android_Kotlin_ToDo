# Android Kotlin ToDo App

## 📌 Overview

A modern To-Do list Android application built with **Kotlin** using **Jetpack Compose** and a clean **Model-View-ViewModel (MVVM)** architecture.

The application supports creating, updating, and deleting tasks, with structured local data persistence powered by **Room**. The project emphasizes clean architecture, dependency injection, asynchronous data handling, and testable design.

This project is based on *AndExplore-Final* by Mark L. Murphy and has been modernized by replacing deprecated libraries, fixing compatibility issues, and improving structure and documentation.

---
## 📱 Screenshots
<p align="center">
  <img src="screenshots/empty_state.jpg" width="250"/>
  <img src="screenshots/create_task.jpg" width="250"/>
  <img src="screenshots/edit_task.jpg" width="250"/>
</p>

<p align="center">
  <img src="screenshots/main_screen.jpg" width="250"/>
  <img src="screenshots/main_screen02.jpg" width="250"/>
</p>



## ✨ Features

- Add new tasks  
- Edit existing tasks  
- Delete tasks  
- Persistent local storage using Room (SQLite abstraction)  

---

## 🛠 Tech Stack

- **Kotlin**  
- **Jetpack Compose**  
- **MVVM Architecture**  
- **Room (SQLite)**  
- **Kotlin Flow**  
- **Koin (Dependency Injection)**  
- **Coroutines**  
- **Unit Testing (JUnit, Mockito)**  
- **Instrumented Testing**

---

## 🏗 Architecture

The application follows a clean MVVM architecture:

- **Composable UI Layer** – Built entirely with Jetpack Compose  
- **ViewModel Layer** – Manages state and business logic  
- **Repository Layer** – Abstracts data access  
- **Room DAO** – Uses `Flow` for reactive queries and `suspend` functions for asynchronous operations  

Dependency injection is managed using **Koin** to ensure modularity and testability.

---

## 🧪 Testing

The project includes coroutine-based unit tests for ViewModel logic and repository interactions, as well as instrumented tests for repository operations with Room.

Testing features include:

- `runTest` for structured coroutine testing  
- Mocked repository verification  
- Flow collection testing  
- Dispatcher control using `StandardTestDispatcher`  
- Instrumented Room database tests to validate Flow emissions and state changes  
- Verification of save, modify, and delete operations in the repository  
- Testing both intermediate flow states and the final repository state  

This ensures correct state management, accurate Flow behavior, and reliable repository interactions in both local and instrumented contexts.


## 📚 What I Learned

Through this project, I strengthened my understanding of:

- Declarative UI development with Jetpack Compose  
- Reactive state management using Flow  
- Asynchronous programming using Coroutines  
- Dependency injection and modular architecture  
- Writing clean, maintainable, and testable Android code  

---

## 🚀 Future Improvements

- Expand unit test coverage  
- Add filtering (All / Completed / Active)
- Enhance UI polish and animations 
- Improve CI/CD integration  
 

---

## ▶️ How to Run

1. Clone the repository  
2. Open the project in Android Studio  
3. Build and run on an emulator or physical device  

---

