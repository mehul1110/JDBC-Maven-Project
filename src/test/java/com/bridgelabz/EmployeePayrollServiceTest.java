package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeePayrollServiceTest {

    @Test
    public void givenSalaryUpdate_whenUpdated_shouldSyncWithDB() {
        EmployeePayrollService service = EmployeePayrollService.getInstance();

        EmployeePayrollData updated = service.updateEmployeeSalaryPrepared("Terisa", 3000000.00);
        EmployeePayrollData fromDB = service.getEmployeePayrollDataByName("Terisa");

        assertNotNull(fromDB);
        assertEquals(updated.salary, fromDB.salary);
        System.out.println("Test Passed! Salary synced with DB: " + fromDB.salary);
    }
}