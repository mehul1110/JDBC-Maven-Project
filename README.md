# Employee Payroll JDBC

## Description
This project is a Java Maven application that manages Employee Payroll data
using JDBC (Java Database Connectivity) and MySQL. It demonstrates how to
perform CRUD operations on a MySQL database from a Java application following
proper Git flow with feature branches. The project is built as part of the
BridgeLabz Java training program.

## Tech Stack
- Java 11
- MySQL 8.0
- JDBC
- Maven
- JUnit 5

## Database Setup

### Step 1: Create Database
```sql
CREATE DATABASE payroll_service;
USE payroll_service;
```

### Step 2: Create Table
```sql
CREATE TABLE employee_payroll (
    id      INT unsigned NOT NULL AUTO_INCREMENT,
    name    VARCHAR(150) NOT NULL,
    gender  CHAR(1),
    salary  DOUBLE NOT NULL,
    start   DATE NOT NULL,
    PRIMARY KEY (id)
);
```

### Step 3: Insert Sample Data
```sql
INSERT INTO employee_payroll (name, gender, salary, start) VALUES
    ('Bill',    'M', 1000000.00, '2018-01-03'),
    ('Terisa',  'F', 3000000.00, '2019-11-13'),
    ('Charlie', 'M', 2000000.00, '2020-05-21');
```

## UC1 - DB Connection
Establishes a connection between the Java application and the
MySQL payroll_service database using JDBC DriverManager.
Verifies the driver is loaded and connection is successful.

## UC2 - Retrieve All Employees
Reads all employee payroll records from the database using
JDBC Statement and ResultSet. Populates a list of
EmployeePayrollData objects and returns them to the caller.

## UC3 - Update Salary using Statement
Updates the base salary for employee Terisa to 3000000.00
using JDBC Statement. After updating, retrieves the record
from DB and verifies the update using a JUnit test.

## UC4 - Update Salary using PreparedStatement + Singleton
Refactors UC3 to use PreparedStatement instead of Statement
for better performance. The EmployeePayrollService class is
made a Singleton so PreparedStatements are cached at the
program, driver and DB level for reuse.

## UC5 - Retrieve Employees by Date Range
Retrieves all employees who joined within a given date range
using PreparedStatement with the BETWEEN clause. Returns a
list of EmployeePayrollData objects matching the date range.

## UC6 - Gender Based Analysis
Performs salary analysis grouped by gender using SQL aggregate
functions SUM, AVG, MIN, MAX and COUNT. Results show a
breakdown of salary statistics for male and female employees.

## How to Run
1. Clone the repo
2. Set up MySQL and run the SQL scripts above
3. Update `USERNAME` and `PASSWORD` in `EmployeePayrollService.java`
4. Run `mvn clean install` to build
5. Run `EmployeePayrollService.java` main method
6. Run `EmployeePayrollServiceTest.java` for JUnit tests