package fi.metropolia.attendancesystem.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeInterface {

    @Insert
    public long insert(Employee employee);

    @Update
    public void update(Employee employee);

    @Delete
    public void delete(Employee employee);

    @Query("SELECT * FROM employee ORDER BY employeeId,employeeName")
    public List <Employee> getAll();

    @Query("SELECT * FROM employee WHERE employeeId LIKE :employeeId")
    public Employee getByEmployeeId (String employeeId);

    @Query("SELECT * FROM employee WHERE employeeName LIKE :employeeName")
    public List <Employee> getByEmployeeName (String employeeName);


}
