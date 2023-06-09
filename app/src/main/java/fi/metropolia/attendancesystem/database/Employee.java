package fi.metropolia.attendancesystem.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The class is defined for defining the employees.
 */
@Entity
public class Employee {
    //Defining fields of the class
    @PrimaryKey
    @NonNull
    private String employeeId;
    private String status;
    private String name;
    private String password;
    private String role;
    /**
     * Id of the latest attendance of the employee. It is used to save the latest attendance ID which will be later used for inserting checkOut time
     */
    private long latestAttendanceId;

    /**
     * Constructor for getting an instance of employee class
     *
     * @param employeeId Employee's Id
     * @param name       Employee's name
     * @param password   Employee's password
     * @param role       Employee's role
     * @param status     Employee's status
     */
    public Employee(@NonNull String employeeId, String name, String password, String role, String status) {
        this.employeeId = employeeId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    /**
     * Method for getting latest attendanceId of the employee.
     *
     * @return latest attendanceId
     */
    public long getLatestAttendanceId() {
        return latestAttendanceId;
    }

    /**
     * Method for setting attendanceId of the employee.
     *
     * @param latestAttendanceId latest Attendance's Id
     */
    public void setLatestAttendanceId(long latestAttendanceId) {
        this.latestAttendanceId = latestAttendanceId;
    }

    /**
     * Method for getting the employeeId of the employee.
     *
     * @return EmployeeId of the employee
     */
    @NonNull
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Method for setting the employeeId of the employee
     *
     * @param employeeId Employee's Id
     */
    public void setEmployeeId(@NonNull String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Method for getting the name of the employee.
     *
     * @return Employee's name
     */
    public String getName() {
        return name;
    }

    /**
     * Method for setting the name of the employee
     *
     * @param name Employee's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for getting the password of the employee
     *
     * @return Employee's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method for setting the password of the employee
     *
     * @param password Employee's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method for getting the status of the employee
     *
     * @return Employee's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Method for setting the status of the employee
     *
     * @param status Employee's status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Method for getting role of the employee
     *
     * @return Employee's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Method for setting role of the employee
     *
     * @param role Employee's role
     */

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Method for getting the employee details
     *
     * @return Employee's details: name, employeeId, role and status.
     */
    @NonNull
    @Override
    public String toString() {
        return name + "  " + employeeId + "  " + "(" + role + ") Status: " + status + " (" + password + ")";
    }
}
