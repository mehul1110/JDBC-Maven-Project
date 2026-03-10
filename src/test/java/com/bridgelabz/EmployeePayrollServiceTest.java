package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeePayrollServiceTest {

    @Test
    public void givenSalaryUpdate_whenUpdated_shouldSyncWithDB() {
        EmployeePayrollService service = new EmployeePayrollService();

        // Update Terisa's salary
        EmployeePayrollData updated = service.updateEmployeeSalary("Terisa", 3000000.00);

        // Retrieve from DB and compare
        EmployeePayrollData fromDB = service.getEmployeePayrollDataByName("Terisa");

        assertEquals(updated.salary, fromDB.salary);
        System.out.println("Test Passed! Salary synced with DB: " + fromDB.salary);
    }
}