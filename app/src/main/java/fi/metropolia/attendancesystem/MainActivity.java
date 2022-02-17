package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "checking main activity";
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        login();

        ImageButton signInBtn = findViewById(R.id.logInButton);
        signInBtn.setOnClickListener(view -> LoginBtnClick());
    }

    public void login() {

        //assigning id for edittext,errorTextView and login button

        String employeeId = ((EditText) findViewById(R.id.userIdText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        TextView errorTextView = findViewById(R.id.errorText);
        ImageButton signInBtn = findViewById(R.id.logInButton);


        database = AppDataBase.getInstance(getApplicationContext());
        //Employee employee = new Employee(0,"000", "Argie", "argie123","esimies" );
        //long id = database.employeeDao().insert(employee);

        /*
        //check existing esimies and avoid multiple entries
        List<Employee> employeeList = database.employeeDao().getAll();
        Log.d(TAG, "is it getting the employee list");
        for (Employee employee1 : employeeList) {
            Log.d(TAG, "is it inside the for loop?");
            if (employee1.getColumnId() > 1 && employee1.getRole().equals("esimies")) {
                Log.d(TAG, "is it trying to delete?");
               database.employeeDao().delete(employee);
            }

        }

         */
    }

        //TODO: Working on the login page Logic, still in the case of employee login the toast is showing wrong username & password even after login

        public void LoginBtnClick() {
            String employeeId = ((EditText) findViewById(R.id.userIdText)).getText().toString();
            String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
            TextView errorTextView = findViewById(R.id.errorText);
            ImageButton signInBtn = findViewById(R.id.logInButton);

            List<Employee> employeeList = database.employeeDao().getAll();

            for (Employee workingEmployee : employeeList) {
                Boolean employerCheck = employeeId.equals(workingEmployee.getEmployeeId()) && password.equals(workingEmployee.getPassword()) && workingEmployee.getRole().equals("esimies");
                Boolean employeeCheck = employeeId.equals(workingEmployee.getEmployeeId()) && password.equals(workingEmployee.getPassword()) && workingEmployee.getRole().equals("worker");

                if (employerCheck) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent employerActivity = new Intent(this, EmployerActivity.class);
                    startActivity(employerActivity);
                    break;
                } else if (employeeCheck) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent employeeActivity = new Intent(this, employeeWindow.class);
                    startActivity(employeeActivity);
                    break;
                } else {
                    errorTextView.setText("Wrong Username or Password");
                    Toast.makeText(this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }

            }
        }





}