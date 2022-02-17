package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class employeeWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_window);

        Button logOut = findViewById(R.id.logOutBtn2);
        logOut.setOnClickListener(view -> finish());
    }
}