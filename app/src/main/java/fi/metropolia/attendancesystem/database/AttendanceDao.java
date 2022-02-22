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


}
