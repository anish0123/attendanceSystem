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
     long insertTime(EmployeeAttendance employeeAttendance);

    @Update
     void update(EmployeeAttendance employeeAttendance);

    @Delete
     void delete(EmployeeAttendance employeeAttendance);

    @Query("SELECT * FROM employee WHERE employeeId = :employeeId")
     ListOfAttendance getAttendance(String employeeId);

    @Query("SELECT * FROM employeeattendance Where attendanceId = :attendanceId")
     EmployeeAttendance getByAttendanceId(long attendanceId);

    @Query("Update EmployeeAttendance Set checkOutTime= :checkOutTime where attendanceId= :attendanceId and employeeId= :employeeId")
     void updateCheckOutTime(String checkOutTime, long attendanceId, String employeeId);

    @Query("Select * From employeeattendance where employeeId= :employeeId Order By attendanceId DESC")
     List<EmployeeAttendance> getAllAttendance(String employeeId);

    @Query("Update EmployeeAttendance Set checkInTime= :checkInTime where attendanceId= :attendanceId and employeeId= :employeeId")
     void updateCheckInTime(String checkInTime, long attendanceId, String employeeId);


}
