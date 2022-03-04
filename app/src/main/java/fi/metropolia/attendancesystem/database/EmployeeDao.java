package fi.metropolia.attendancesystem.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This interface is created to get data about employeee from database through the queries created in this interface
 */
@Dao
public interface EmployeeDao {

    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Query("Delete FROM employee where employeeId= :employeeId ")
    void deleteEmployee(String employeeId);

    @Query("UPDATE employee SET status= :status WHERE employeeId = :employeeId")
    void updateEmployee(String employeeId, String status);

    @Query("SELECT * FROM employee ORDER BY employeeId")
     List<Employee> getAll();

    @Query("SELECT * FROM employee WHERE employeeId like :employeeId")
     Employee getByEmployeeId(String employeeId);

    @Query("SELECT * FROM employee WHERE employeeId like :employeeId and password = :password")
     Employee checkLogIn(String employeeId, String password);

    @Query("SELECT * FROM employee WHERE status like :status")
     Employee getStatus(String status);

    @Query("SELECT * FROM employee WHERE name like :name")
     List<Employee> getByName(String name);

    @Query("SELECT * FROM employee WHERE role like :role")
     List<Employee> getByRole(String role);

    @Query("UPDATE employee SET attendanceId = :attendanceId WHERE employeeId = :employeeId")
    void updateAttendanceId(String employeeId, long attendanceId);

    @Query("SELECT * FROM employee WHERE attendanceId like :attendanceId")
     Employee getByEmployeeId(long attendanceId);




}
