package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import fi.metropolia.attendancesystem.database.AppDataBase;

/**
 * This class is created for the activity where the employee can check their working history.
 */
public class Employee_history extends AppCompatActivity {
    private AppDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_history);
        //Database is introduced for getting employee history.
        dataBase = AppDataBase.getInstance(getApplicationContext());
        historyUI();
        //SignOut button is introduced and used for logging out the employee accounts.
        Button signOutButton = findViewById(R.id.signOut2);
        signOutButton.setOnClickListener(view -> backToMain());
    }

    /**
     * Method for displaying the employee attendance history in the list view.
     */
    private void historyUI() {
        TextView textView = findViewById(R.id.employeeHistoryId);
        Intent intent = getIntent();
        String employeeId = intent.getStringExtra(EmployeeWindow.EMPLOYEE_ID_SEND);
        String employeeDetail = intent.getStringExtra(EmployeeWindow.EMPLOYEE_DETAIL);
        textView.setText(employeeDetail);
        ListView listView = findViewById(R.id.historyList);
        listView.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dataBase.attendanceDao().getAllAttendance(employeeId)
        ));
    }

    /**
     * Method for going back to the main activity.
     */
    private void backToMain() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}