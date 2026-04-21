# 🏦 Mini Banking CLI

A command-line banking application built in Java, simulating core banking operations such as account management, fund transfers, and an admin panel — backed by a MySQL relational database.

---

## Overview

Mini Banking CLI is a console-based application that simulates a simplified banking system. Users can register at a bank, manage their account, and perform financial transactions. An admin panel allows privileged users to approve new clients, manage banks, and view a full audit trail of all activity.

---

## Features

### Client
- Register at a chosen bank (account, card, and IBAN generated automatically)
- Deposit and withdraw money
- Transfer money to another account via IBAN
- View full account details (balance, card info, IBAN)

### Admin
- Approve or reject pending client registrations
- Create additional admin accounts
- Add new banks to the system
- View full audit trail or filter by client

### System
- Luhn-compliant card number generation (Visa / Mastercard)
- MOD 97 IBAN validation (Romanian format)
- Transaction management with rollback on failure
- Audit trail logging for all key operations

---

## Tech Stack

| Layer        | Technology              |
|--------------|-------------------------|
| Language     | Java 17                 |
| Build tool   | Maven                   |
| Database     | MySQL                   |
| DB Access    | JDBC (raw)              |
| Testing      | JUnit 5                 |

---

## Architecture

The project follows a layered architecture pattern:

```
┌─────────────────────────────────────┐
│              Main (CLI)             │  ← Entry point, menu navigation
├─────────────────────────────────────┤
│           Service Layer             │  ← Business logic (interfaces + impl)
├─────────────────────────────────────┤
│             DAO Layer               │  ← Database access (JDBC)
├─────────────────────────────────────┤
│          DatabaseConnection         │  ← MySQL connection via JDBC
└─────────────────────────────────────┘
```

**Package structure:**
```
src/main/java/
├── main/         # Application entry point
├── service/      # Service interfaces
│   └── impl/     # Service implementations
├── dao/          # Data Access Objects
├── model/        # Domain models (Client, Account, Card, IBAN, Bank, AuditTrail)
├── enums/        # Role, Status
├── exceptions/   # Custom exception classes
├── db/           # Database connection
└── utils/        # Utility methods (number generation, audit logging)
```

---
## Usage

On startup, the application presents three options:

```
Welcome!
1. Register
2. Login
3. Admin
0. Exit
```

**Registering** — select a bank from the list, fill in your personal details and credentials. Your account, card, and IBAN are generated automatically. Your account will be in `PENDING` status until approved by an admin.

**Logging in** — enter your username and password to access deposit, withdraw, transfer, and account detail features.

**Admin panel** — accessible by username only (no password prompt). Requires the account to have the `ADMIN` role.

---

## Running Tests

The project includes unit tests for IBAN generation and validation:

```bash
mvn test
```

Tests cover:
- IBAN length (28 characters for Romanian format)
- IBAN structural format (`^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$`)
- MOD 97 validity check
- Uniqueness of generated IBANs

---

## ⚠️ Known Limitations

- Passwords are stored as plaintext — BCrypt hashing is planned for a future update
- Database credentials are currently hardcoded — environment variable support is planned
- Card and IBAN uniqueness is partially enforced in-memory; database `UNIQUE` constraints are recommended in production
