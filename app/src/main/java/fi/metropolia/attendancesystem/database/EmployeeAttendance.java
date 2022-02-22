package fi.metropolia.attendancesystem.database;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Employee.class,
        onDelete = CASCADE,
        parentColumns = "employeeId",
        childColumns = "employeeId"
))
public class EmployeeAttendance {

    @PrimaryKey(autoGenerate = true)
    private int attendanceId;

    private String employeeId;
    private String checkInTime;
    private String checkOutTime;


    public EmployeeAttendance(int attendanceId, String employeeId, String checkInTime, String checkOutTime) {
        this.attendanceId = attendanceId;
        this.employeeId = employeeId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
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

    public String toString(){
        return checkInTime+" "+checkOutTime;
    }


}
