package fi.metropolia.attendancesystem.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * The class is created to get the list of all the attendance and connect two tables from database.
 */
public class ListOfAttendance {
    //Defining fields of the class
    @Embedded
    private Employee employee;

    @Relation(parentColumn = "employeeId", entityColumn = "employeeId")
    private List<EmployeeAttendance> employeeAttendances;

    /**
     * Method for getting employee
     *
     * @return employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Method for getting list of Employee Attendance
     *
     * @return employee attendances
     */
    public List<EmployeeAttendance> getEmployeeAttendances() {
        return employeeAttendances;
    }

    /**
     * Method for setting the employee attendances
     *
     * @param employeeAttendances employee attendances
     */
    public void setEmployeeAttendances(List<EmployeeAttendance> employeeAttendances) {
        this.employeeAttendances = employeeAttendances;
    }

    /**
     * Method for setting the employee
     *
     * @param employee employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
