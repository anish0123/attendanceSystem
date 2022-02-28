package fi.metropolia.attendancesystem.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The class is defined for defining the employees.
 */
@Entity
public class Employee {
    @PrimaryKey
    @NonNull
    private String employeeId;
    private String status;
    private String name;
    private String password;
    private String role;
    private long attendanceId;

    /**
     * Constructor for getting an instance of employee class
     * @param employeeId String
     * @param name String
     * @param password String
     * @param role String
     * @param status String
     */
    public Employee( String employeeId, String name, String password, String role,String status) {
        this.employeeId = employeeId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.status=status;
    }

    /**
     * Method for getting attendanceId of the employee.
     * @return attendanceId
     */
    public long getAttendanceId() {
        return attendanceId;
    }

    /**
     * Method for setting attendanceId of the employee.
     * @param attendanceId String
     */
    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }

    /**
     * Method for getting the employeeId of the employee.
     * @return employeeId String
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Method for setting the employeeId of the employee
     * @param employeeId String
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Method for getting the name of the employee.
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * Method for setting the name of the employee
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for getting the password of the employee
     * @return password String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method for setting the password of the employee
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method for getting the status of the employee
     * @return status String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Method for setting the status of the employee
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Method for getting role of the employee
     * @return role String
     */
    public String getRole() {
        return role;
    }

    /**
     * Method for setting role of the employee
     * @param role String
     */

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Method for getting the employee details
     * @return employee String
     */
    @Override
    public String toString() {
        return name+ "  " + employeeId+ "  " + "(" + role + ") Status: "+ status ;
    }
}
