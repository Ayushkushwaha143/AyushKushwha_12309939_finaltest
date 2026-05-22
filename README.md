# Smart Loan & Banking Management System

A console-based Java application developed for managing customers, bank accounts, loans, transactions, and interest calculations for rural banking operations. This project is designed using core Java concepts including OOP, Collections Framework, File Handling, and Exception Handling.

---

## 📌 Project Overview

FinNova Bank is digitizing its rural banking system to solve problems like:

* Incorrect interest calculations
* Duplicate customer records
* Missing transaction history
* Loan repayment confusion
* Poor file management

This project provides a complete banking and loan management solution using Java.

---

## 🚀 Features

### 👤 Customer Management

* Add new customers
* View customer details
* Search customer by ID

### 🏦 Bank Account Operations

* Deposit money
* Withdraw money
* Check balance
* Transaction history management

### 💰 Loan Management

* Create loans
* Loan eligibility checking
* Simple Interest calculation
* Compound Interest calculation

### 📊 Reports

* Total customers
* Total loans issued
* Highest loan amount
* Total bank balance
* Interest generated
* Customer with highest loan

### 📁 File Handling

* Stores data in text files
* Loads records automatically on startup
* Saves updated records on exit

### ⚠️ Exception Handling

Custom exceptions included:

* `InvalidAmountException`
* `InsufficientBalanceException`
* `InvalidLoanException`

---

## 🛠️ Technologies Used

* Java
* OOP Concepts
* Collections Framework
* File Handling
* Exception Handling
* Mathematical Logic

---

## 📂 Project Structure

```bash
SmartLoan/
│
├── src/
│   ├── Main.java
│   ├── Person.java
│   ├── Customer.java
│   ├── Loan.java
│   ├── BankAccount.java
│   ├── FileHandler.java
│   ├── InvalidAmountException.java
│   ├── InsufficientBalanceException.java
│   └── InvalidLoanException.java
│
├── customers.txt
├── loans.txt
├── transactions.txt
│
└── README.md
```

---

## 🧠 OOPS Concepts Used

* Abstraction
* Inheritance
* Polymorphism
* Encapsulation
* Method Overriding
* Constructor Overloading

---

## 📚 Collections Used

| Purpose             | Collection |
| ------------------- | ---------- |
| Store Customers     | HashMap    |
| Store Loans         | ArrayList  |
| Transaction History | LinkedList |
| Loan Reports        | TreeMap    |

---

## 🧮 Interest Formulas

### Simple Interest

SI=\frac{P\times R\times T}{100}

### Compound Interest

CI=P\left(1+\frac{R}{100}\right)^T-P

Where:

* **P** = Principal Amount
* **R** = Rate of Interest
* **T** = Time in Years

---

## 📋 Menu Driven Functionalities

```text
===== FinNova Bank =====

1. Add Customer
2. View Customers
3. Create Loan
4. Calculate Simple Interest
5. Calculate Compound Interest
6. Deposit Money
7. Withdraw Money
8. View Transactions
9. Search Customer
10. Generate Reports
11. Exit
```

---

## 🔐 Loan Eligibility Rules

| Salary Range      | Maximum Loan |
| ----------------- | ------------ |
| Below ₹25,000     | ₹2,00,000    |
| ₹25,000 – ₹50,000 | ₹5,00,000    |
| Above ₹50,000     | ₹10,00,000   |

If loan amount exceeds eligibility, the system throws:
`InvalidLoanException`

---

## ▶️ How to Run

1. Clone the repository

```bash
git clone https://github.com/your-username/SmartLoan.git
```

2. Open project in Eclipse / IntelliJ IDEA

3. Compile and run `Main.java`

---

## 📸 Sample Output

```text
WELCOME TO FINNOVA BANK

1. Add Customer
2. View Customers
3. Create Loan
...
```

---

## 🎯 Learning Outcomes

This project helps in understanding:

* Real-world Java application development
* File-based data persistence
* Banking system logic
* Exception handling techniques
* Java Collections Framework
* Console-based UI development

---

## 📄 Assessment Reference

This project is based on the Java Story-Based Coding Assessment provided in the question paper. 

---

## 👨‍💻 Author

**Ayush Kushwaha**
B.Tech CSE Student
