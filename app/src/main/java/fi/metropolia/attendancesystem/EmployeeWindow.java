package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;
import fi.metropolia.attendancesystem.database.EmployeeAttendance;

public class EmployeeWindow extends AppCompatActivity {
    final Handler handler = new Handler();
    private AppDataBase database;
    public static final String EMPLOYEE_ID_SEND = "employee_id";
    public static final String EMPLOYEE_DETAIL = "employee_detail";
    public static final String TAG ="test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_window);
        //Getting the intend and string from main activity.
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EMPLOYEE_LOGIN);
        //Introducing the database
        database = AppDataBase.getInstance(getApplicationContext());

        //Setting employeeTextView to display the employee details taken from main activity.
        TextView employeeTextView = findViewById(R.id.employeePageText);
        employeeTextView.setText(message);

        //Submit button, logOut button and historyButton is introduced and used for submitting attendance, signing out and check history.
        ImageButton submitButton = findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(view -> submitButtonClick());
        Button logOut = findViewById(R.id.logOutBtn2);
        logOut.setOnClickListener(view -> backToMain());
        Button historyButton = findViewById(R.id.historyBtn);
        historyButton.setOnClickListener(view -> historyButtonClick());
    }

    /**
     * Method for submitting the checkIn time or checkOut time according to which radio button is selected.
     */
    private void submitButtonClick() {
        RadioGroup checkInRG = findViewById(R.id.checkRadioGroup);

        if (checkInRG.getCheckedRadioButtonId() == R.id.checkInRadio) {
            checkInButtonClick();
        }

        if (checkInRG.getCheckedRadioButtonId() == R.id.checkOutRadio) {
            checkOutButtonClick();
        }
    }

    /**
     * Method for saving the checkIn time while user selects radio button checkIn
     */
    private void checkInButtonClick() {
        // getting intent and employeeId as String from main activity.
        Intent intent = getIntent();
        String employeesWindow = intent.getStringExtra(MainActivity.EMPLOYEE_ID);
        //Formatting the checkIn time and date.
        String formatted = dateFormat(System.currentTimeMillis());
        //Creating a new employee attendance when an employee checkIn.
        EmployeeAttendance employeeAttendance = new EmployeeAttendance(0, employeesWindow, formatted, "");
        long id = database.attendanceDao().insertTime(employeeAttendance);
        //Displaying the checkIn time and date at textView.
        TextView checkInDisplay = findViewById(R.id.timeView);
        checkInDisplay.setText(getString(R.string.checkedInAt, formatted));
        database.employeeDao().updateAttendanceId(employeesWindow, id);

    }

    /**
     * Method for saving the checkOut time while user selects radio button checkOut
     */
    private void checkOutButtonClick() {
        Intent intent = getIntent();
        String employeesWindow = intent.getStringExtra(MainActivity.EMPLOYEE_ID);
        //Declared a employee so that we can get employee details through employee ID.
        Employee employee = database.employeeDao().getByEmployeeId(employeesWindow);
        // called get attendanceId for employee so that we can get the latest attendance Id for submitting checkOut.
        long id = employee.getAttendanceId();
        EmployeeAttendance employeeAttendance = database.attendanceDao().getByAttendanceId(id);
        TextView checkInDisplay = findViewById(R.id.timeView);
        String formatted = dateFormat(System.currentTimeMillis());
        //Calling for checkIn time for finding out duration between checkIn and checkOut Time
        long checkInTime = convertToEpoch(employeeAttendance.getCheckInTime());
        long duration = System.currentTimeMillis()-checkInTime;

        if(employeeAttendance.getCheckOutTime().equals("")) {
            database.attendanceDao().updateCheckOutTime(formatted, id, employeesWindow);
            database.attendanceDao().updateDuration(getDuration(duration), id, employeesWindow);
            checkInDisplay.setText(getString(R.string.checkedOutAt, formatted, getDuration(duration)));
        }
    }

    /**
     * Method of opening the employee history activity when the viewHistory button is clicked
     */
    private void historyButtonClick(){
        Intent intent = getIntent();
        String employeeName = intent.getStringExtra(MainActivity.EMPLOYEE_LOGIN);
        String employeeId = intent.getStringExtra(MainActivity.EMPLOYEE_ID);
        Intent historyActivity = new Intent(this, Employee_history.class);
        historyActivity.putExtra(EMPLOYEE_ID_SEND, employeeId);
        historyActivity.putExtra(EMPLOYEE_DETAIL, employeeName);
        startActivity(historyActivity);
    }

    /**
     *Method onPause is called so that when employee activity is on pause for 60 seconds it goes back to main activity
     * @see  <a href="https://www.codegrepper.com/code-examples/java/android++delay+for+3+seconds">postDelay</a>
     */
    @Override
    protected void onPause() {
        super.onPause();

        // got idea from this page https://www.codegrepper.com/code-examples/java/android++delay+for+3+seconds
        handler.postDelayed(this::finish, 60000);
    }

    /**
     * Method for going back to main activity
     */
    private void backToMain(){
        Intent mainActivity = new Intent(this,MainActivity.class);

        startActivity(mainActivity);
    }

    /**
     * Method for formatting the checkIn and checkOut time
     * @see <a href ="https://stackoverflow.com/questions/7740972/convert-epoch-time-to-date"> Date formatting</a>
     * @param epochTime time in milli seconds
     * @return time in locale.Uk date format ("dd/MM/yyyy HH:mm:ss")
     */
    public String dateFormat (long epochTime) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        format.setTimeZone(TimeZone.getTimeZone("EET"));
        return format.format(epochTime);
    }

    /**
     * changing String date format into epoch time format)
     * @param changeDate date in String format ("dd/MM/yyyy HH:mm:ss", Locale.UK)
     * @return epoch time in milliseconds
     * @see <a href ="https://www.geeksforgeeks.org/simpledateformat-parse-method-in-java-with-examples/#:~:text=The%20parse()%20Method%20of,given%20by%20a%20start%20position.">String date format into epoch</a>
     */
    public long convertToEpoch (String changeDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        Date date = null;
        try {
            date = df.parse(changeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        long epoch = date.getTime();
        Log.d(TAG,String.valueOf(epoch));
        Log.d(TAG,dateFormat(epoch));
        return epoch;
    }


    /**
     * Convert milliseconds into String format (HH hours mm minutes ss seconds)
     * @param millis  milliseconds that needs to be converted into String
     * @return duration in String format (HH hours mm minutes ss seconds)
     * @see <a href = "https://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java">Convert epoch times into String</a>
     */
    public String getDuration(long millis) {
        if(millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
       return getString(R.string.duration,hours,minutes,seconds);
    }



    /**
     * Disable Physical back button
     * Avoids chances of moving back to signed it status after signOut button is Pressed
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Button Disabled", Toast.LENGTH_SHORT).show();

    }
}