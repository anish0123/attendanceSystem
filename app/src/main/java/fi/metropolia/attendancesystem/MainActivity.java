package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;

public class MainActivity extends AppCompatActivity {
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //login();
    }

    public void login() {

        //assigning id for edittext,errorTextView and login button
        EditText employeeId = findViewById(R.id.userIdText);
        EditText password = findViewById(R.id.passwordText);
        TextView errorTextView = findViewById(R.id.errorText);
        ImageButton signInBtn = findViewById(R.id.logInButton);

        database = AppDataBase.getInstance(getApplicationContext());

        Employee employee = new Employee("000", "Argie", "esimies", "argie123");


        long id = database.employeeInterface().insert(employee);

        //check existing esimies and avoid multiple entries
        List<Employee> employeeList = database.employeeInterface().getAll();
        for (Employee employee1 : employeeList) {
            if (employee1.getColumnId() > 1 && employee1.getEmployeeRole().equals("esimies")) {
                database.employeeInterface().delete(employee);
            }

        }

    }

}