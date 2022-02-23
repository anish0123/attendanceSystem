package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;

public class EmployerViewHistoryActivity extends AppCompatActivity {
    private AppDataBase database;
    public static final String TAG = "EmployerViewHistory";
    public static final String EmployeeID = "employeeId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view_history);
        database=AppDataBase.getInstance(getApplicationContext());
        historyUI();
        Button signOutButton = findViewById(R.id.signOut2);
        signOutButton.setOnClickListener(view -> backToMain());
    }
    public void historyUI(){
        TextView textView = findViewById(R.id.employeeHistoryId);
        Intent intent = getIntent();
        //Getting the Id from Employer Activity
        Integer Id = intent.getIntExtra(EmployerActivity.ID, 0);
        //Since our employerId is in String so we had to change Int to String
        String workingId = getString(R.string.changeIntToString,String.valueOf(Id));
        //Introduced employee for getting the details of the employee.
        Employee employee = database.employeeDao().getByEmployeeId(workingId);
        String employeeId = employee.getEmployeeId();

        textView.setText(employee.toString());
        //Introduced ListView for displaying the work history of employee
        ListView historyList = findViewById(R.id.historyList);
        historyList.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                database.attendanceDao().getAllAttendance(employeeId)
        ));

        historyList.setOnItemClickListener(((adapterView, view, i, l) -> {
            Log.d(TAG, "did the click listener work?" + i);
            startActivityEmployeeHistory(i);
        }));

    }

    //Method for starting the employer edit activity.
    private void startActivityEmployeeHistory(int i) {
        Intent intent = new Intent(EmployerViewHistoryActivity.this, EmployerEdit.class);
        intent.putExtra(EmployeeID, i);

        startActivity(intent);

    }

    public void backToMain(){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
    }


}
