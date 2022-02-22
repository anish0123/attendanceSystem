package fi.metropolia.attendancesystem.database;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface AttendanceDao {

    @Query("SELECT * FROM employee WHERE employeeId = :employeeId")
    public ListOfAttendance getAttendance(String employeeId);

}
