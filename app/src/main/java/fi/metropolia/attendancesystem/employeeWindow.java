package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;
import fi.metropolia.attendancesystem.database.EmployeeAttendance;

public class employeeWindow extends AppCompatActivity {
    final Handler handler = new Handler();
    private AppDataBase database;
    private static final String TAG = "Employee Window";
    public static final String EMPLOYEE_ID_SEND = "employee_id";
    public static final String EMPLOYEE_DETAIL = "employee_detail";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_window);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EMPLOYEE_LOGIN);
        database = AppDataBase.getInstance(getApplicationContext());

        ImageButton submitButton =findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(view -> submitButtonClick());
        Button logOut = findViewById(R.id.logOutBtn2);
        logOut.setOnClickListener(view ->backToMain());

        TextView employeeTextView = findViewById(R.id.employeePageText);
        employeeTextView.setText(message);

        Button historyButton = findViewById(R.id.historyBtn);
        historyButton.setOnClickListener(view -> historyButtonClick());


    }

    public void submitButtonClick() {
        RadioGroup checkInRG = findViewById(R.id.checkRadioGroup);

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
        long epochTime = System.currentTimeMillis();
        //For date formatting
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        format.setTimeZone(TimeZone.getTimeZone("EET"));
        String formatted = format.format(epochTime);
        EmployeeAttendance employeeAttendance = new EmployeeAttendance(0, employeesWindow, formatted, "");
        long id = database.attendanceDao().insertTime(employeeAttendance);
        TextView checkInDisplay = findViewById(R.id.timeView);
        checkInDisplay.setText(getString(R.string.checkedInAt, formatted));
        database.employeeDao().updateAttendanceId(employeesWindow,id);

    }

    public void checkOutButtonClick() {
        Intent intent = getIntent();
        String employeesWindow = intent.getStringExtra(MainActivity.EMPLOYEE_ID);
        //Declared a employee so that we can get employee details through employee ID.
        Employee employee = database.employeeDao().getByEmployeeId(employeesWindow);
        // called get attendanceId for employee so that we can get the latest attendance Id for submitting checkOut.
        long id = employee.getAttendanceId();
        long checkOutTime = System.currentTimeMillis();
        TextView checkInDisplay = findViewById(R.id.timeView);


        //For date formatting
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        format.setTimeZone(TimeZone.getTimeZone("EET"));
        String formatted = format.format(checkOutTime);
        checkInDisplay.setText(getString(R.string.checkedOutAt, formatted));
        database.attendanceDao().updateCheckOutTime(formatted,id,employeesWindow);


    }
    public void historyButtonClick(){
        Intent intent = getIntent();
        String employeeName = intent.getStringExtra(MainActivity.EMPLOYEE_LOGIN);
        String employeeId = intent.getStringExtra(MainActivity.EMPLOYEE_ID);

        Intent historyActivity = new Intent(this,Employee_history.class);
        historyActivity.putExtra(EMPLOYEE_ID_SEND,employeeId);
        historyActivity.putExtra(EMPLOYEE_DETAIL,employeeName);
        startActivity(historyActivity);
    }

    protected void onPause() {
        super.onPause();

        // got idea from this page https://www.codegrepper.com/code-examples/java/android++delay+for+3+seconds
        handler.postDelayed(this::finish, 60000);

    }
    public void backToMain(){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
    }
    }