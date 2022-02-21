package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fi.metropolia.attendancesystem.database.Employee;

public class employeeWindow extends AppCompatActivity {
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_window);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EMPLOYEE_LOGIN);
        String employeesWindow = intent.getStringExtra(MainActivity.EMPLOYEE_ID);



        Button logOut = findViewById(R.id.logOutBtn2);
        logOut.setOnClickListener(view ->finish());

        TextView employeeTextView = findViewById(R.id.employeePageText);
        employeeTextView.setText(message);
    }

    protected void onPause() {
        super.onPause();

        // got idea from this page https://www.codegrepper.com/code-examples/java/android++delay+for+3+seconds
        handler.postDelayed(() -> finish(), 60000);

    }
    }