package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;
import fi.metropolia.attendancesystem.database.EmployeeAttendance;

public class EmployerViewHistory extends AppCompatActivity {
    private AppDataBase database;
    public static final String EMPLOYEEID = "employeeId";
    public static final String ATTENDANCEID = "attendanceId";
    private String employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view_history);
        //Introducing database
        database=AppDataBase.getInstance(getApplicationContext());
        historyUI();
        //Introducing signOut button for logging out
        Button signOutButton = findViewById(R.id.signOut2);
        signOutButton.setOnClickListener(view -> backToMain());
    }

    /**
     * Method of showing the employee attendance of an employee in the listView
     */
    private void historyUI(){
        TextView detailView = findViewById(R.id.employeeHistoryId);
        Intent intent = getIntent();
        //Since our employerId is in String so we had to change Int to String
        employeeId = intent.getStringExtra(EmployerActivity.EMPLOYEE_ID);
        //Introduced employee for getting the details of the employee.
        Employee employee = database.employeeDao().getByEmployeeId(employeeId);
        detailView.setText(employee.toString());
        //Introduced ListView for displaying the work history of employee
        ListView historyList = findViewById(R.id.historyList);
        historyList.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                database.attendanceDao().getAllAttendance(employeeId)
        ));
        historyList.setOnItemClickListener(((adapterView, view, i, l) -> startActivityEmployeeHistory(i)));
    }

    /**
     * Method for starting the employerEdit activity for editing the checkIn and checkOut time of the employee.
     * @param i the number for the clicked employee attendance from the listView.
     */
    private void startActivityEmployeeHistory(int i) {
        Intent intent = new Intent(EmployerViewHistory.this, EmployerEdit.class);
        List<EmployeeAttendance> list = database.attendanceDao().getAllAttendance(employeeId);
        EmployeeAttendance employeeAttendance = list.get(i);
        long attendanceId = employeeAttendance.getAttendanceId();
        intent.putExtra(ATTENDANCEID,attendanceId);
        intent.putExtra(EMPLOYEEID, employeeId);
        startActivity(intent);

    }

    /**
     * Method for going to the main activity
     */
    private void backToMain(){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
    }

    /**
     * Method onResume is used so that it updates the listView when user comes back to this activity after employerEdit activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        historyUI();
    }



}
