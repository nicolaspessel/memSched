# 🗂️ memSched

> Simulates an OS Kernel memory and process scheduler. Built for learning and curiosity purposes.

![Status](https://img.shields.io/badge/status-completed-green)
![Stack](https://img.shields.io/badge/stack-Java%20-orange)
![Learning Focus](https://img.shields.io/badge/focus-computer_architecture-red)

---

## 📌 Overview

This project simulates a memory and process scheduler, part of an OS Kernel, based on the developer curiosity and 
experience with Unix-like systems. The goal is to create an OOP structure with Records and Interfaces, combined with a 
computer architecture logic and a CLI interactive visualizer for learning purposes.

---

## 🎯 Goals

### Product Goals
- [x] Simulates the memory and process scheduler of a Unix-like system
- [x] Customize priority and required memory weights when swapping an existing process
- [x] Implement custom scheduling strategies based on the Interface created and Polymorphism

### Learning Goals
- [x] Understand OOP architecture within a Java ecosystem
- [x] Use different OOP Java structures, such as Records and Interfaces
- [x] Practice the use of objects with the main data structures, such as Maps, Arrays and their variations

---

## 🧠 Architecture & Key Decisions

### High-Level Architecture

```
  User / CLI visualizer
      ↓ (p) add process
  Kernel - allocates RAM → MemoryManager
      ↓ creates PCB  
  ReadyQueue (LinkedList - FIFO)
      ↓ selects next process 
  SchedulingInterface ← Dependency Injection
```

### Decision Log

Wrote in [docs/ARCHITECTURE.md](https://github.com/nicolaspessel/memSched/blob/main/docs/ARCHITECTURE.md).

---

## 🚀 Getting Started

### Prerequisites

`Recommended JDK for Java 21 features (Records, Sequenced Collections)
Java SE Development Kit (JDK) >= 21`

### Installation

`git clone [https://github.com/your-username/project-name.git](https://github.com/your-username/project-name.git)
cd project-name`


### Running Locally

1. Compile all source files

    `javac -d bin src/*.java`


2. Run the simulation application

    `java -cp bin App`

---

## 📚 What I Learned

### Things that worked well
- Scheduling interface, polymorphism and DI;
- Interactive clock control;
- Process and memory management.

### Things I'd do differently
- Implement threads for asynchronous events;
- Create a log of swapped projects and their characteristics.

### Concepts that clicked through building this
- Java OOP;
- Records, interfaces, maps and dynamic arrays;
- Dependency injection.

---

## ⚠️ Known Limitations

- Can not operate automatically without the user
- Do not use threads to optimize asynchronous event like in a real system
- Relies on a simple and limited CLI visualizer

---

## 👤 Contacts

*Built by Nícolas Pessel · [GitHub](https://github.com/nicolaspessel) · [LinkedIn](https://www.linkedin.com/in/nicolaspessel/)* 
