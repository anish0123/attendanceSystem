package fi.metropolia.attendancesystem.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public Employee( String employeeId, String name, String password, String role,String status) {
        this.employeeId = employeeId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.status=status;
    }

    public long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return name+ "  " + employeeId+ "  " + "(" + role + ") Status: "+ status ;
    }
}
