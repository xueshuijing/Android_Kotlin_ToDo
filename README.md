# Android Kotlin ToDo App

## 📌 Overview

A modern To-Do list Android application built with **Kotlin** using **Jetpack Compose** and a clean **Model-View-ViewModel (MVVM)** architecture.

The application supports creating, updating, and deleting tasks, with structured local data persistence powered by **Room**. The project emphasizes clean architecture, dependency injection, asynchronous data handling, and testable design.

This project is based on *AndExplore-Final* by Mark L. Murphy and has been modernized by replacing deprecated libraries, fixing compatibility issues, and improving structure and documentation.

---
## 📱 Screenshots
![Screenshot_2026-03-04-18-39-52-06_2f937a25d6d72e388c5662add9dda914](https://github.com/user-attachments/assets/015356f4-6d8a-4a64-abc0-2665c61d551b)
![Screenshot_2026-03-04-18-40-23-66_2f937a25d6d72e388c5662add9dda914](https://github.com/user-attachments/assets/e1ad2610-71b4-41ec-a58d-7019dfaab420)
![Screenshot_2026-03-04-18-41-09-97_2f937a25d6d72e388c5662add9dda914](https://github.com/user-attachments/assets/5afb0a86-f923-4cb3-a947-c285ff947647)
![Screenshot_2026-03-04-18-41-17-64_2f937a25d6d72e388c5662add9dda914](https://github.com/user-attachments/assets/a3cb20d0-8a73-4b00-bdcc-2cc98bebd692)
![Screenshot_2026-03-04-18-43-08-09_2f937a25d6d72e388c5662add9dda914](https://github.com/user-attachments/assets/4f23a279-b9de-48cd-a09f-3abe2230063d)



## ✨ Features

- Add new tasks  
- Edit existing tasks  
- Delete tasks  
- Persistent local storage using Room (SQLite abstraction)  
- Reactive UI updates using Kotlin Flow  
- Modern UI built entirely with Jetpack Compose  
- Clean MVVM architecture  
- Dependency injection with Koin  
- Coroutine-based unit testing  

---

## 🛠 Tech Stack

- Kotlin  
- Jetpack Compose  
- Android SDK  
- Room (SQLite abstraction layer)  
- Kotlin Coroutines & Flow  
- Koin (Dependency Injection)  
- JUnit & Mockito (Unit Testing)  

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

The project includes coroutine-based unit tests for ViewModel logic and repository interactions.

Testing features include:

- `runTest` for structured coroutine testing  
- Mocked repository verification  
- Flow collection testing  
- Dispatcher control using `StandardTestDispatcher`  

This ensures correct state management and correct interaction behaviour.

---

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
- Add UI testing (Compose testing framework)  
- Improve CI/CD integration  
- Enhance UI polish and animations  

---

## ▶️ How to Run

1. Clone the repository  
2. Open the project in Android Studio  
3. Build and run on an emulator or physical device  

---

## 👤 Author

Developed by Xue Jing Sie  
