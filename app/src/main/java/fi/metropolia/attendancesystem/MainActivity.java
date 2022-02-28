package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    public static final String EMPLOYEE_LOGIN = "employee_login";
    public static final String EMPLOYEE_ID = "employee_id";
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDataBase.getInstance(getApplicationContext());


        //add manager into database for time-being manually commented after first run
        /*
        Employee employee = new Employee("000", "Manager", "boss123", "esimies","active");
        database.employeeDao().insert(employee);
         */

        ImageButton infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(view -> infoBtnClick());


        ImageButton signInBtn = findViewById(R.id.logInButton);
        signInBtn.setOnClickListener(view -> LoginBtnClick());

    }

    public void infoBtnClick(){
        Intent info= new Intent(this, Info.class);
        startActivity(info);
    }


    // checks employeeID & password from database and logIn to respective activity for employee and employer based on roles mentioned on the database
    public void LoginBtnClick() {
        String employeeId = ((EditText) findViewById(R.id.userIdText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        TextView errorTextView = findViewById(R.id.errorText);


        Employee employee = database.employeeDao().checkLogIn(employeeId, password);
        Log.d(TAG, "employee? " + employee);
        if (employee != null) {
            if (employee.getRole().equals("esimies")&&employee.getStatus().equals("active")) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                Intent employerActivity = new Intent(this, EmployerActivity.class);
                startActivity(employerActivity);
            } else if (employee.getRole().equals("worker")&&employee.getStatus().equals("active")) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent employeeActivity = new Intent(this, employeeWindow.class);
                String message = "Welcome, " + employee.getName() + " (" + employee.getEmployeeId() + ")";
                employeeActivity.putExtra(EMPLOYEE_LOGIN, message);
                employeeActivity.putExtra(EMPLOYEE_ID, employee.getEmployeeId());
                startActivity(employeeActivity);
            }else {
                Toast.makeText(this, "User Inactive !!! ", Toast.LENGTH_SHORT).show();
                errorTextView.setText("User Inactive !!!");

            }

        } else {
            Toast.makeText(this, "Wrong Credentials !!! ", Toast.LENGTH_SHORT).show();
            errorTextView.setText("Wrong Credentials !!!");

        }

    }


    protected void onResume() {
        super.onResume();
        EditText idText = findViewById(R.id.userIdText);
        EditText password = findViewById(R.id.passwordText);
        TextView errorText = findViewById(R.id.errorText);
        idText.setText("");
        password.setText("");
        errorText.setText("");
    }


}