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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight= displayMetrics.heightPixels;

        getWindow().setLayout((int)(screenWidth),(int)(screenHeight*.7));
        updateUI();

        Button editBtn = findViewById(R.id.editButton);
        Button cancelBtn = findViewById(R.id.cancelButton);

        editBtn.setOnClickListener(view -> editClick());
        cancelBtn.setOnClickListener(view -> this.finish());


    }

    private void editClick() {
        RadioGroup editGroup = findViewById(R.id.checkEditGroup);
        EditText checkInEdit = findViewById(R.id.checkInTimeEdit);
        EditText checkOutEdit = findViewById(R.id.checkOutTimeEdit);


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

    private void updateUI() {
        EmployeeAttendance employeeAttendance = database.attendanceDao().getByAttendanceId(attendanceId);
        TextView textDisplay = findViewById(R.id.textDisplay);
        textDisplay.setText(employeeAttendance.toString());
    }



}