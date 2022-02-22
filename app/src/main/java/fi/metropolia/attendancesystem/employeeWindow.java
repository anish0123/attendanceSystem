package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;
import fi.metropolia.attendancesystem.database.EmployeeAttendance;

public class employeeWindow extends AppCompatActivity {
    final Handler handler = new Handler();
    private AppDataBase database;
    private static final String TAG = "Employee Window";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_window);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EMPLOYEE_LOGIN);
        String employeesWindow = intent.getStringExtra(MainActivity.EMPLOYEE_ID);
        database = AppDataBase.getInstance(getApplicationContext());

        ImageButton submitButton =findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(view -> submitButtonClick());
        Button logOut = findViewById(R.id.logOutBtn2);
        logOut.setOnClickListener(view ->finish());

        TextView employeeTextView = findViewById(R.id.employeePageText);
        employeeTextView.setText(message);


    }

    public void submitButtonClick() {
        RadioGroup checkInRG = findViewById(R.id.checkRadioGroup);
        RadioButton checkInButton = findViewById(R.id.checkInRadio);
        RadioButton checkOutButton = findViewById(R.id.checkOutRadio);


        if(checkInRG.getCheckedRadioButtonId() == R.id.checkInRadio) {
          checkInButtonClick();
        }
    }

    public void checkInButtonClick() {
        Intent intent = getIntent();
        String employeesWindow = intent.getStringExtra(MainActivity.EMPLOYEE_ID);
        Log.d(TAG, "working till here? 1");
        EmployeeAttendance employeeAttendance = new EmployeeAttendance(0, employeesWindow, "113", "224");
        long id = database.attendanceDao().insertTime(employeeAttendance);
        TextView checkInDisplay = findViewById(R.id.timeView);
        checkInDisplay.setText("Checked In at: " + employeeAttendance.getCheckInTime());
    }

    protected void onPause() {
        super.onPause();

        // got idea from this page https://www.codegrepper.com/code-examples/java/android++delay+for+3+seconds
        handler.postDelayed(() -> finish(), 60000);

    }
    }