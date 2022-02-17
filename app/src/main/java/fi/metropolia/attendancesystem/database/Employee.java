package fi.metropolia.attendancesystem.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Employee {
    @PrimaryKey(autoGenerate = true)
    private long columnId;
    private String employeeId;
    private String name;
    private String password;
    private String role;

    public Employee(long columnId, String employeeId, String name, String password, String role) {
        this.columnId = columnId;
        this.employeeId = employeeId;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public long getColumnId() {
        return columnId;
    }

    public void setColumnId(long columnId) {
        this.columnId = columnId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return name+ "  " + employeeId+ "  " + "(" + role + ")" ;
    }
}
