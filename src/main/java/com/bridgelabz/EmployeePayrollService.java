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

    // Singleton instance
    private static EmployeePayrollService instance;

    // Cached PreparedStatements
    private PreparedStatement updateSalaryStatement;
    private PreparedStatement getEmployeeByNameStatement;

    // Private constructor for Singleton
    private EmployeePayrollService() {
        try {
            prepareStatements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Singleton getInstance method
    public static EmployeePayrollService getInstance() {
        if (instance == null) {
            instance = new EmployeePayrollService();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        System.out.println("Connection established: " + con);
        return con;
    }

    // Cache PreparedStatements at program level
    private void prepareStatements() throws SQLException {
        Connection con = getConnection();

        updateSalaryStatement = con.prepareStatement(
                "UPDATE employee_payroll SET salary = ? WHERE name = ?");

        getEmployeeByNameStatement = con.prepareStatement(
                "SELECT * FROM employee_payroll WHERE name = ?");

        System.out.println("PreparedStatements cached successfully!");
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

    // UC4 - Update using PreparedStatement
    public EmployeePayrollData updateEmployeeSalaryPrepared(String name, double salary) {
        try {
            updateSalaryStatement.setDouble(1, salary);
            updateSalaryStatement.setString(2, name);
            int rowsAffected = updateSalaryStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Salary updated using PreparedStatement for: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getEmployeePayrollDataByName(name);
    }

    // Reusing ResultSet with cached PreparedStatement
    public EmployeePayrollData getEmployeePayrollDataByName(String name) {
        try {
            getEmployeeByNameStatement.setString(1, name);
            ResultSet rs = getEmployeeByNameStatement.executeQuery();

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

    public List<EmployeePayrollData> getEmployeesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        String sql = "SELECT * FROM employee_payroll WHERE start BETWEEN ? AND ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                LocalDate start = rs.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, start));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    public static void main(String[] args) {
        EmployeePayrollService service = EmployeePayrollService.getInstance();

        // UC5 - Retrieve by date range
        System.out.println("\n=== Employees joined between 2018-01-01 and 2019-12-31 ===");
        List<EmployeePayrollData> empList = service.getEmployeesByDateRange(
                LocalDate.of(2018, 1, 1), LocalDate.of(2019, 12, 31));
        empList.forEach(System.out::println);
    }
}