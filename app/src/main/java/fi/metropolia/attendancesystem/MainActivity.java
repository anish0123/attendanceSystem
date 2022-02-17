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


        ImageButton signInBtn = findViewById(R.id.logInButton);
        signInBtn.setOnClickListener(view -> LoginBtnClick());
    }


    //TODO: Working on the login page Logic, still in the case of employee login the toast is showing wrong username & password even after login

    public void LoginBtnClick() {
        String employeeId = ((EditText) findViewById(R.id.userIdText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        TextView errorTextView = findViewById(R.id.errorText);

        database = AppDataBase.getInstance(getApplicationContext());

        List<Employee> employeeList = database.employeeDao().getAll();

        for (Employee workingEmployee : employeeList) {
            //  Boolean employerCheck = employeeId.equals(workingEmployee.getEmployeeId()) && password.equals(workingEmployee.getPassword()) && workingEmployee.getRole().equals("esimies");
            // Boolean employeeCheck = employeeId.equals(workingEmployee.getEmployeeId()) && password.equals(workingEmployee.getPassword()) && workingEmployee.getRole().equals("worker");

            Boolean credentialsCheck = employeeId.equals(workingEmployee.getEmployeeId()) && password.equals(workingEmployee.getPassword());
            if (credentialsCheck) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                if (workingEmployee.getRole().equals("esimies")) {
                    Intent employerActivity = new Intent(this, EmployerActivity.class);
                    startActivity(employerActivity);
                } else if (workingEmployee.getRole().equals("worker")) {
                    Intent employeeActivity = new Intent(this, employeeWindow.class);
                    startActivity(employeeActivity);
                }

                break;
            } else {
                errorTextView.setText("Wrong Username or Password");
            }

        }
    }
    protected  void onResume() {
        super.onResume();
        EditText idText = findViewById(R.id.userIdText);
        EditText password = findViewById(R.id.passwordText);

        idText.setText("");
        password.setText("");
    }
}