package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {

    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/payroll_service?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456"; // change this

    public Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        System.out.println("Connection established: " + con);
        return con;
    }

    public List<EmployeePayrollData> getEmployeePayrollData() {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        String sql = "SELECT * FROM employee_payroll";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                LocalDate startDate = rs.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    public EmployeePayrollData updateEmployeeSalary(String name, double salary) {
        String sql = String.format(
                "UPDATE employee_payroll SET salary = %.2f WHERE name = '%s'", salary, name);

        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Salary updated successfully for: " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getEmployeePayrollDataByName(name);
    }

    public EmployeePayrollData getEmployeePayrollDataByName(String name) {
        String sql = "SELECT * FROM employee_payroll WHERE name = '" + name + "'";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int id = rs.getInt("id");
                double salary = rs.getDouble("salary");
                LocalDate startDate = rs.getDate("start").toLocalDate();
                return new EmployeePayrollData(id, name, salary, startDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        EmployeePayrollService service = new EmployeePayrollService();

        // UC2 - Retrieve all
        System.out.println("=== All Employees ===");
        service.getEmployeePayrollData().forEach(System.out::println);

        // UC3 - Update Terisa's salary
        System.out.println("\n=== Updating Terisa's Salary ===");
        EmployeePayrollData updated = service.updateEmployeeSalary("Terisa", 3000000.00);
        System.out.println("Updated: " + updated);
    }
}