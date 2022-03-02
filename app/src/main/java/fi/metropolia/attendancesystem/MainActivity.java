package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;


public class MainActivity extends AppCompatActivity {

    public static final String EMPLOYEE_LOGIN = "employee_login";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYER_LOGIN = "employer_login";
    public static final String EMPLOYER_ID = "employer_id";
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDataBase.getInstance(getApplicationContext());


        ImageButton infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(view -> infoBtnClick());


        ImageButton signInBtn = findViewById(R.id.logInButton);
        signInBtn.setOnClickListener(view -> emptyFieldCheck());

        ImageButton addSuperVisor = findViewById(R.id.addSuperVisorBtn);
        addSuperVisor.setOnClickListener(view -> addSuperVisor());

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
        if (employee != null) {
            if (employee.getRole().equals(getString(R.string.esimies))&&employee.getStatus().equals(getString(R.string.active))) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent employerActivity = new Intent(this, EmployerActivity.class);
                String message = getString(R.string.welcomeEmployeeDetails,  employee.getName(),  employee.getEmployeeId());
                employerActivity.putExtra(EMPLOYER_LOGIN, message);
                employerActivity.putExtra(EMPLOYER_ID, employee.getEmployeeId());
                startActivity(employerActivity);
            } else if (employee.getRole().equals(getString(R.string.worker))&&employee.getStatus().equals(getString(R.string.active))) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent employeeActivity = new Intent(this, EmployeeWindow.class);
                String message = getString(R.string.welcomeEmployeeDetails,  employee.getName(),  employee.getEmployeeId());
                employeeActivity.putExtra(EMPLOYEE_LOGIN, message);
                employeeActivity.putExtra(EMPLOYEE_ID, employee.getEmployeeId());
                startActivity(employeeActivity);
            }else {
                Toast.makeText(this, "User Inactive !!! ", Toast.LENGTH_SHORT).show();
                errorTextView.setText(R.string.userInactive);

            }

        } else {
            Toast.makeText(this, "Wrong Credentials !!! ", Toast.LENGTH_SHORT).show();
            errorTextView.setText(R.string.wrongCredentials);

        }

    }
    public void addSuperVisor(){
        Intent intent = new Intent(this, AddSuperVisor.class);
        startActivity(intent);
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

    public void emptyFieldCheck(){
        String employeeId = ((EditText) findViewById(R.id.userIdText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        TextView errorTextView = findViewById(R.id.errorText);
        if (employeeId.equals("")||password.equals("")){
            errorTextView.setText(R.string.empty_field_message);
        } else {
            LoginBtnClick();
        }
    }


}