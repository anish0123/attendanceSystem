package fi.metropolia.attendancesystem.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Insert
    public Long insert (Employee employee);

    @Update
    public void update (Employee employee);

    @Delete
    public void delete (Employee employee);

    @Query("SELECT * FROM employee ORDER BY columnId, employeeId")
    public List <Employee> getAll();

    @Query("SELECT * FROM employee WHERE employeeId like :employeeId")
    public Employee getByEmployeeId (String employeeId);

    @Query("SELECT * FROM employee WHERE name like :name")
    public List <Employee>  getByName (String name);

    @Query("SELECT * FROM employee WHERE role like :role")
    public List <Employee> getByRole (String role);
}
