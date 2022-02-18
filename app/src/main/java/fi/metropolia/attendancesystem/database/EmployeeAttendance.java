package fi.metropolia.attendancesystem.database;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Employee.class,
        onDelete=CASCADE,
        parentColumns = "employeeId",
        childColumns ="employeeId"
        ))
public class EmployeeAttendance {

    @PrimaryKey(autoGenerate = true)
    private long attendanceId;

    private String checkInTime;
    private String checkOutTime;

    private String employeeId;

    public EmployeeAttendance(long attendanceId, String checkInTime, String checkOutTime, String employeeId) {
        this.attendanceId = attendanceId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.employeeId = employeeId;
    }

    public long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
