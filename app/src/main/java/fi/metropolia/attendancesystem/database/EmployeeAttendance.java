package fi.metropolia.attendancesystem.database;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

/**
 * This class is defined for recording employee attendance of each working employee in the database.
 */
@Entity(foreignKeys = @ForeignKey(
        entity = Employee.class,
        onDelete = CASCADE,
        parentColumns = "employeeId",
        childColumns = "employeeId"
))
public class EmployeeAttendance {

    @PrimaryKey(autoGenerate = true)
    private long attendanceId;
    private String employeeId;
    /**
     * employee checkIn time in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     */
    private String checkInTime;
    /**
     * employee checkOut time in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     */
    private String checkOutTime;
    private String timeDuration;


    /**
     * Constructor created for getting the instance of the EmployeeAttendance class.
     *
     * @param attendanceId a unique auto generated attendance ID for each attendance of each employee
     * @param employeeId   a unique employee ID given to each employee
     * @param checkInTime  time when the employee checks in at work in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     * @param checkOutTime time when the employee checks out of work in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     */
    public EmployeeAttendance(long attendanceId, String employeeId, String checkInTime, String checkOutTime) {
        this.attendanceId = attendanceId;
        this.employeeId = employeeId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    /**
     * Method for getting the unique attendance Id
     *
     * @return attendance Id of the selected attendance day.
     */
    public long getAttendanceId() {
        return attendanceId;
    }

    /**
     * Method for setting the unique attendance Id
     *
     * @param attendanceId attendance Id of the attendance day which needs to be changed.
     */
    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    /**
     * Method for getting the checkIn time of the employee attendance
     *
     * @return checkIn Time in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     */
    public String getCheckInTime() {
        return checkInTime;
    }

    /**
     * Method for setting the checkIn time of the employee attendance
     *
     * @param checkInTime checkIn time in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     */
    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    /**
     * Method for getting the checkOut time of the employee attendance
     *
     * @return checkOut time in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     */
    public String getCheckOutTime() {
        return checkOutTime;
    }

    /**
     * Method for setting the checkOut time of the employee attendance
     *
     * @param checkOutTime checkOut time in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     */
    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    /**
     * Method for getting the employee Id of an employee
     *
     * @return employee Id of the employee
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Method for setting the employee Id of employee
     *
     * @param employeeId employee Id
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    /**
     * Method for providing details of the employee attendance
     *
     * @return checkIn time and checkOut time of the attendance in locale.Uk date format ("dd/MM/yyyy HH:mm:ss") and duration of total work hours
     */
    public String toString() {
        return "In: " + checkInTime + "   Out: " + checkOutTime + "\n \n Duration: " + timeDuration + "\n";
    }


}
