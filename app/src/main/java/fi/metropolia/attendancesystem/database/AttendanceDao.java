package fi.metropolia.attendancesystem.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface AttendanceDao {

    @Insert
    public long insertTime (EmployeeAttendance employeeAttendance);

    @Update
    public void update (EmployeeAttendance employeeAttendance);

    @Delete
    public void delete (EmployeeAttendance employeeAttendance);

    @Query("SELECT * FROM employee WHERE employeeId = :employeeId")
    public ListOfAttendance getAttendance(String employeeId);

    @Query("SELECT * FROM employeeattendance Where attendanceId = :attendanceId")
    public EmployeeAttendance getByAttendanceId (long attendanceId);

    @Query("Update EmployeeAttendance Set checkOutTime= :checkOutTime where attendanceId= :attendanceId and employeeId= :employeeId")
    public  void updateCheckOutTime(String checkOutTime, long attendanceId,String employeeId);

    @Query("Select * From employeeattendance where employeeId= :employeeId Order By attendanceId DESC")
    public List<EmployeeAttendance> getAllAttendance(String employeeId);

    @Query("Update EmployeeAttendance Set checkInTime= :checkInTime where attendanceId= :attendanceId and employeeId= :employeeId")
    public  void updateCheckInTime(String checkInTime, long attendanceId,String employeeId);






}
