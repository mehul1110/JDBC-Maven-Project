package com.payroll;

import java.sql.*;

public class EmployeePayrollService {

    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";


    public Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        System.out.println("Connection established: " + con);
        return con;
    }

    public static void main(String[] args) {
        EmployeePayrollService service = new EmployeePayrollService();
        try {
            service.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}