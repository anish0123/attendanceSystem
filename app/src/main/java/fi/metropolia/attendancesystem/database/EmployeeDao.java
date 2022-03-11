package fi.metropolia.attendancesystem.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This interface is created to get data about employee from database through the queries created in this interface
 */
@Dao
public interface EmployeeDao {

    /**
     * Method for inserting new employee in the database
     *
     * @param employee employee that needs to be added.
     *                 employee is defined more in Employee class
     */
    @Insert
    void insert(Employee employee);

    /**
     * Method for updating employees
     *
     * @param employee employee that needs to be updated
     */
    @Update
    void update(Employee employee);

    /**
     * Method for deleting the employees.
     *
     * @param employeeId employee that needs to be deleted.
     */
    @Query("Delete FROM employee where employeeId= :employeeId ")
    void deleteEmployee(String employeeId);

    /**
     * Method for updating the employee's status
     *
     * @param employeeId Id of the employee whose status needs to be updated.
     * @param status     status of the employee that needs to be updated.
     */
    @Query("UPDATE employee SET status= :status WHERE employeeId = :employeeId")
    void updateEmployee(String employeeId, String status);

    /**
     * Method for getting list of all employees.
     *
     * @return list of employees that are in the database
     */
    @Query("SELECT * FROM employee ORDER BY employeeId")
    List<Employee> getAll();

    /**
     * Method for getting employee by employee's Id
     *
     * @param employeeId Id of the employee that needs to be selected
     * @return employee that has same employeeId as the parameter.
     */
    @Query("SELECT * FROM employee WHERE employeeId like :employeeId")
    Employee getByEmployeeId(String employeeId);

    /**
     * Method for logging in employees if their employeeId and password matches with the database
     *
     * @param employeeId Id of the employee
     * @param password   password of the employee
     * @return employee whose Id and password matches.
     */
    @Query("SELECT * FROM employee WHERE employeeId like :employeeId and password = :password")
    Employee checkLogIn(String employeeId, String password);

    /**
     * Method for updating the attendance Id to the latest one.
     * Whenever an employee checks in, new attendance Id is created and that will be also be saved in employeeDao to be later used while employee checks Out.
     *
     * @param employeeId         Id of the employee
     * @param latestAttendanceId latest Id of the employee's attendance
     */
    @Query("UPDATE employee SET latestAttendanceId = :latestAttendanceId WHERE employeeId = :employeeId")
    void updateAttendanceId(String employeeId, long latestAttendanceId);


}
