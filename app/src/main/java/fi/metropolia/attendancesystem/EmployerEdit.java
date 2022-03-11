package fi.metropolia.attendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.EmployeeAttendance;

/**
 * This class is created for the activity where employer can edit the time of the employees
 */
public class EmployerEdit extends AppCompatActivity {
    private AppDataBase database;
    long attendanceId;
    String employeeId;

    /**
     * Popup window is created in this onCreate method
     *
     * @see <a href="https://www.youtube.com/watch?v=fn5OlqQuOCk">Popup Window</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_edit);
        database = AppDataBase.getInstance(getApplicationContext());
        //Getting intent from EmployerViewHistory activity
        Intent intent = getIntent();
        attendanceId = intent.getLongExtra(EmployerViewHistory.ATTENDANCEID, 0);
        employeeId = intent.getStringExtra(EmployerViewHistory.EMPLOYEEID);

        //Introduces display metrics for the popup window
        //Credit: https://www.youtube.com/watch?v=fn5OlqQuOCk
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        getWindow().setLayout(screenWidth, (int) (screenHeight * .7));
        updateUI();

        //Buttons are introduced for editing the checkIn and checkOut time and closing the popup
        Button editBtn = findViewById(R.id.editButton);
        Button cancelBtn = findViewById(R.id.cancelButton);
        Button deleteBtn = findViewById(R.id.deleteButton);

        editBtn.setOnClickListener(view -> editClick());
        cancelBtn.setOnClickListener(view -> this.finish());
        deleteBtn.setOnClickListener(view -> deleteEmployeeAttendance());
    }

    /**
     * Method for editing the checkIn and checkOut time according to which radio button is selected.
     */
    private void editClick() {
        RadioGroup editGroup = findViewById(R.id.checkEditGroup);
        EditText checkInEdit = findViewById(R.id.checkInTimeEdit);
        EditText checkOutEdit = findViewById(R.id.checkOutTimeEdit);

        //If else statement for checking which radio is button is selected and editing checkIn and checkOut according to it.
        if (editGroup.getCheckedRadioButtonId() == R.id.checkInRadioEdit) {
            database.attendanceDao().updateCheckInTime(checkInEdit.getText().toString(), attendanceId, employeeId);
            calculateDifference();
        } else if (editGroup.getCheckedRadioButtonId() == R.id.checkOutRadioEdit) {
            database.attendanceDao().updateCheckOutTime(checkOutEdit.getText().toString(), attendanceId, employeeId);
            calculateDifference();
        } else if (editGroup.getCheckedRadioButtonId() == R.id.bothRadio) {
            database.attendanceDao().updateCheckInTime(checkInEdit.getText().toString(), attendanceId, employeeId);
            database.attendanceDao().updateCheckOutTime(checkOutEdit.getText().toString(), attendanceId, employeeId);
            calculateDifference();
        }
    }

    /**
     * Method for updating the User interface
     */
    private void updateUI() {
        EmployeeAttendance employeeAttendance = database.attendanceDao().getByAttendanceId(attendanceId);
        TextView textDisplay = findViewById(R.id.textDisplay);
        textDisplay.setText(employeeAttendance.toString());
    }

    /**
     * Method for delete employee attendance if employee was not present but had checkIn and checkOut time.
     */
    private void deleteEmployeeAttendance() {
        database.attendanceDao().delete(database.attendanceDao().getByAttendanceId(attendanceId));
        this.finish();

    }

    /**
     * Method for calculating difference between checkIn and checkOut time after editing the time
     */
    private void calculateDifference() {
        EmployeeWindow employeeWindow = new EmployeeWindow();
        EmployeeAttendance employeeAttendance = database.attendanceDao().getByAttendanceId(attendanceId);
        //Converting checkIn and checkOut time into epoch for calculating the difference
        long checkInTime = employeeWindow.convertToEpoch(employeeAttendance.getCheckInTime());
        long checkOutTime = employeeWindow.convertToEpoch(employeeAttendance.getCheckOutTime());
        long durationTime = checkOutTime - checkInTime;
        String duration = getDuration(durationTime);
        database.attendanceDao().updateDuration(duration, attendanceId, employeeId);
        this.finish();

    }

    /**
     * Convert milliseconds into String format (HH hours mm minutes ss seconds)
     *
     * @param millis milliseconds that needs to be converted into String
     * @return duration in String format (HH hours mm minutes ss seconds)
     * @see <a href = "https://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java">Convert epoch times into String</a>
     */
    public String getDuration(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        return getString(R.string.duration, hours, minutes, seconds);
    }

}