package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {

    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/payroll_service?useSSL=false&allowPublicKeyRetrieval=true";;
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

    public static void main(String[] args) {
        EmployeePayrollService service = new EmployeePayrollService();
        List<EmployeePayrollData> employees = service.getEmployeePayrollData();
        System.out.println("Employee Payroll Data: ");
        employees.forEach(System.out::println);
    }
}