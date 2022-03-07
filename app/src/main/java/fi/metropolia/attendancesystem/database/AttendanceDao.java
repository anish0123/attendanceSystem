package fi.metropolia.attendancesystem.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * The interface is created to get data from database about employee attendance through the queries created in this interface
 */
@Dao
public interface AttendanceDao {

    /**
     * Method for inserting new employee's attendance
     *
     * @param employeeAttendance new attendance of the employee
     * @return Id of the new attendance
     */
    @Insert
    long insertTime(EmployeeAttendance employeeAttendance);

    /**
     * Method for updating the employee's attendance
     *
     * @param employeeAttendance employee's attendance that needs to be updated.
     *                           employeeAttendance is defined in detail in EmployeeAttendance class
     */
    @Update
    void update(EmployeeAttendance employeeAttendance);

    /**
     * Method for deleting the employee's attendance
     *
     * @param employeeAttendance employee's attendance that needs to be deleted.
     */
    @Delete
    void delete(EmployeeAttendance employeeAttendance);

    /**
     * Method for getting the list of all the attendances according to the employee's Id
     *
     * @param employeeId Id of the employee whose list of attendances needs to be printed
     * @return list of attendance of an employee
     */
    @Query("SELECT * FROM employee WHERE employeeId = :employeeId")
    ListOfAttendance getAttendance(String employeeId);

    /**
     * Method for getting the employee attendance according to the attendance ID
     *
     * @param attendanceId Attendance Id
     * @return employee attendance which has same as attendance Id as parameter
     */
    @Query("SELECT * FROM employeeattendance Where attendanceId = :attendanceId")
    EmployeeAttendance getByAttendanceId(long attendanceId);

    /**
     * Method for updating the checkOut time
     *
     * @param checkOutTime checkOut time: time when user checks out in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     * @param attendanceId attendance Id that needs to update checkOut time.
     * @param employeeId   Id of the employee who chose to do the checkOut
     */
    @Query("Update EmployeeAttendance Set checkOutTime= :checkOutTime where attendanceId= :attendanceId and employeeId= :employeeId")
    void updateCheckOutTime(String checkOutTime, long attendanceId, String employeeId);

    /**
     * Method for getting the list of attendances of an employee
     *
     * @param employeeId Id of the employee whose list of attendance needs to be printed
     * @return list of attendance of the selected employee
     */
    @Query("Select * From employeeattendance where employeeId= :employeeId Order By attendanceId DESC")
    List<EmployeeAttendance> getAllAttendance(String employeeId);

    /**
     * Method for updating the checkIn time
     *
     * @param checkInTime  checkIn time: time when user checks in in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     * @param attendanceId attendance Id that needs to update checkIn time.
     * @param employeeId   Id of the employee whose checkIn time needs to be updated.
     */
    @Query("Update EmployeeAttendance Set checkInTime= :checkInTime  where attendanceId= :attendanceId and employeeId= :employeeId")
    void updateCheckInTime(String checkInTime, long attendanceId, String employeeId);

    /**
     * Method for updating the time duration between checkIn and checkOut time.
     *
     * @param timeDuration time duration between checkIn and checkOut time in String format (HH hours mm minutes ss seconds)
     * @param attendanceId attendance Id that needs to update time duration.
     * @param employeeId   Id of the employee whose time duration needs to be updated.
     */
    @Query("Update EmployeeAttendance Set timeDuration =:timeDuration where attendanceId= :attendanceId and employeeId= :employeeId")
    void updateDuration(String timeDuration, long attendanceId, String employeeId);
}
