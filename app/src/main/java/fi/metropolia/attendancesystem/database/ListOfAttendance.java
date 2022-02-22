package fi.metropolia.attendancesystem.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ListOfAttendance {
    @Embedded
    private Employee employee;

    @Relation(parentColumn = "employeeId", entityColumn ="employeeId")
    private List<EmployeeAttendance> employeeAttendances;

    public Employee getEmployee() {
        return employee;
    }

    public List<EmployeeAttendance> getEmployeeAttendances() {
        return employeeAttendances;
    }

    public void setEmployeeAttendances(List<EmployeeAttendance> employeeAttendances) {
        this.employeeAttendances = employeeAttendances;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
