package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.EmployeeAttendance;

public class EmployerEdit extends AppCompatActivity {
    private AppDataBase database;
    long attendanceId;
    String employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_edit);
        database = AppDataBase.getInstance(getApplicationContext());
        Intent intent = getIntent();
        attendanceId = intent.getLongExtra(EmployerViewHistory.ATTENDANCEID,0);
        employeeId = intent.getStringExtra(EmployerViewHistory.EMPLOYEEID);

        //Introduces display metrics for the popup window
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight= displayMetrics.heightPixels;

        getWindow().setLayout(screenWidth,(int)(screenHeight*.7));
        updateUI();

        //Buttons are introduced for editing the checkIn and checkOut time and closing the popup
        Button editBtn = findViewById(R.id.editButton);
        Button cancelBtn = findViewById(R.id.cancelButton);
        Button absentBtn = findViewById(R.id.absentButton);

        editBtn.setOnClickListener(view -> editClick());
        cancelBtn.setOnClickListener(view -> this.finish());
        absentBtn.setOnClickListener(view -> makeEmployeeAttendanceAbsent());
    }

    /**
     * Method for editing the checkIn and checkOut time according to which radio button is selected.
     */
    private void editClick() {
        RadioGroup editGroup = findViewById(R.id.checkEditGroup);
        EditText checkInEdit = findViewById(R.id.checkInTimeEdit);
        EditText checkOutEdit = findViewById(R.id.checkOutTimeEdit);

        //If else statement for checking which radio is button is selected and editing checkIn and checkOut according to it.
        if(editGroup.getCheckedRadioButtonId() == R.id.checkInRadioEdit) {
            database.attendanceDao().updateCheckInTime(checkInEdit.getText().toString(), attendanceId, employeeId);
            this.finish();
        } else if (editGroup.getCheckedRadioButtonId() == R.id.checkOutRadioEdit ) {
            database.attendanceDao().updateCheckOutTime(checkOutEdit.getText().toString(), attendanceId, employeeId);
           this.finish();
        } else if (editGroup.getCheckedRadioButtonId() == R.id.bothRadio) {
            database.attendanceDao().updateCheckInTime(checkInEdit.getText().toString(), attendanceId, employeeId);
            database.attendanceDao().updateCheckInTime(checkInEdit.getText().toString(), attendanceId, employeeId);
            this.finish();
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
     * Method for updating employee attendance as absent if employee was not present but had checkIn and checkOut time.
     */
    private void makeEmployeeAttendanceAbsent(){
        database.attendanceDao().updateCheckInTime(getString(R.string.absent),attendanceId,employeeId);
        database.attendanceDao().updateCheckOutTime(getString(R.string.absent),attendanceId,employeeId);
        this.finish();
    }


}