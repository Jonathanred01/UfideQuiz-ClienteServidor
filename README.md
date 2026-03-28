# Multiplayer Trivia Game (Client-Server Java)

Real-time multiplayer trivia game built with a client-server architecture using Java sockets.

This project simulates a distributed system where a central server manages game logic, player states, and scoring, while multiple clients connect and compete simultaneously.

---

## Overview

This system was designed to solve the lack of simple, customizable multiplayer trivia platforms.

Key characteristics:
- Real-time multiplayer interaction
- Custom question handling
- Centralized game logic
- Private game sessions

---

## Architecture

The application follows a **client-server model**:

### Server
- Manages game flow
- Sends questions to clients
- Processes answers
- Updates scores in real time

### Client
- Connects to server via sockets
- Displays questions and options
- Sends responses
- Shows live scoreboard

Communication is handled through a **custom message protocol over sockets**.

---

## Features

- Multiplayer game room management
- Player tracking and states
- Turn-based question system
- Real-time scoring updates
- Winner detection and game termination
- Desktop UI using Java Swing
- Exception handling for system stability
- Secure data handling (basic encryption support)
- Persistent data storage (database integration)

---

## Technologies

- Java
- Java Sockets (TCP/IP)
- Swing (GUI)
- MVC Architecture
- Object-Oriented Programming (OOP)
- Database integration

---

## How It Works

1. The server initializes the game session  
2. Clients connect to the server  
3. The server sends a question to all players  
4. Clients respond through socket communication  
5. The server processes answers and updates scores  
6. After all rounds, a winner is declared  

---

## Project Structure
/server
/client
/model
/view
/controller
/database

---

## Key Concepts Demonstrated

- Client-server communication  
- Concurrent user handling  
- Real-time data synchronization  
- Protocol design over sockets  
- Separation of concerns (MVC)  
- Robust exception handling  

---

## Installation & Execution

### Requirements
- Java JDK 8+

### Run Server
```bash
java Server.java

---

## Key Concepts Demonstrated

- Client-server communication  
- Concurrent user handling  
- Real-time data synchronization  
- Protocol design over sockets  
- Separation of concerns (MVC)  
- Robust exception handling  

---

## Installation & Execution

### Requirements
- Java JDK 8+

### Run Server
```bash
java Server.java
java Client.java
Future Improvements
Web-based client (React / API backend)
Authentication system
Improved encryption
Scalable architecture (microservices)
Matchmaking system
Team

Allan Fauricio Fonseca Batista
Final Note

This project represents a practical implementation of distributed systems concepts, focusing on real-time interaction, concurrency, and structured backend logic.

