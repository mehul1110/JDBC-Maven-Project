package com.bridgelabz;

import java.util.List;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeePayrollServiceTest {

    @Test
    public void givenDateRange_whenRetrieved_shouldReturnEmployees() {
        EmployeePayrollService service = EmployeePayrollService.getInstance();

        List<EmployeePayrollData> employees = service.getEmployeesByDateRange(
                LocalDate.of(2018, 1, 1), LocalDate.of(2019, 12, 31));

        assertNotNull(employees);
        assertTrue(employees.size() > 0);
        System.out.println("Test Passed! Employees in date range: " + employees.size());
        employees.forEach(System.out::println);
    }

    @Test
    public void givenEmployees_whenAnalysedByGender_shouldReturnCorrectValues() {
        EmployeePayrollService service = EmployeePayrollService.getInstance();
        System.out.println("=== Gender Analysis ===");
        assertDoesNotThrow(() -> service.getEmployeesByGenderAnalysis());
        System.out.println("Test Passed! Gender analysis successful");
    }

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