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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;
import fi.metropolia.attendancesystem.database.EmployeeAttendance;

public class employeeWindow extends AppCompatActivity {
    final Handler handler = new Handler();
    private AppDataBase database;
    private static final String TAG = "Employee Window";
    private long epochTime;
    int checkInId;


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
        epochTime = System.currentTimeMillis();
        Log.d(TAG, String.valueOf(epochTime));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(epochTime);
        Log.d(TAG,formatted);


    }

    public void submitButtonClick() {
        RadioGroup checkInRG = findViewById(R.id.checkRadioGroup);
        RadioButton checkInButton = findViewById(R.id.checkInRadio);
        RadioButton checkOutButton = findViewById(R.id.checkOutRadio);


        if(checkInRG.getCheckedRadioButtonId() == R.id.checkInRadio) {
          checkInButtonClick();
        }

        if(checkInRG.getCheckedRadioButtonId() == R.id.checkOutRadio) {
            checkOutButtonClick();
        }
    }

    public void checkInButtonClick() {
        Intent intent = getIntent();
        String employeesWindow = intent.getStringExtra(MainActivity.EMPLOYEE_ID);
        epochTime = System.currentTimeMillis();
        EmployeeAttendance employeeAttendance = new EmployeeAttendance(0, employeesWindow, String.valueOf(epochTime), "");
        long id = database.attendanceDao().insertTime(employeeAttendance);
        TextView checkInDisplay = findViewById(R.id.timeView);
        checkInId= database.attendanceDao().getByAttendanceId();
        Log.d(TAG,String.valueOf(checkInId));
        //For date formatting
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(epochTime);
        checkInDisplay.setText("Checked In at: " + formatted);
    }

    public void checkOutButtonClick() {
        Intent intent = getIntent();
        String employeesWindow = intent.getStringExtra(MainActivity.EMPLOYEE_ID);
        long checkOutTime = System.currentTimeMillis();

        database.attendanceDao().updateCheckOutTime(String.valueOf(checkOutTime),checkInId);
        TextView checkInDisplay = findViewById(R.id.timeView);
        //For date formatting
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(epochTime);
        checkInDisplay.setText("Checked Out at: " + formatted);
    }

    protected void onPause() {
        super.onPause();

        // got idea from this page https://www.codegrepper.com/code-examples/java/android++delay+for+3+seconds
        handler.postDelayed(() -> finish(), 60000);

    }
    }