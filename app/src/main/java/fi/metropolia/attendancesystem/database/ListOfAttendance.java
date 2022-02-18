package fi.metropolia.attendancesystem.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ListOfAttendance {
    @Embedded
    private Employee employee;

    @Relation(parentColumn = "employeeId", entityColumn ="employeeId")
    private List<EmployeeAttendance> employeeAttendances;


}
