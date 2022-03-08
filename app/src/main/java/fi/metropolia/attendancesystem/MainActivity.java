package fi.metropolia.attendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;

/**
 * The class is created for the main activity when employees and employer can log in to their account.
 * Employer can sign up for the first time or add other employer later in this activity.
 */
public class MainActivity extends AppCompatActivity {
    //Defining strings to put it in as extras in intent
    public static final String EMPLOYEE_LOGIN = "employee_login";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYER_LOGIN = "employer_login";
    public static final String EMPLOYER_ID = "employer_id";
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Database introduced
        database = AppDataBase.getInstance(getApplicationContext());

        //info Button, signIn Button, addSuperVisor Button is introduced for opening info activity, signing in employees and employers and adding supervisor or manager when clicked
        ImageButton infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(view -> infoBtnClick());
        ImageButton signInBtn = findViewById(R.id.logInButton);
        signInBtn.setOnClickListener(view -> emptyFieldCheck());
        ImageButton addSuperVisor = findViewById(R.id.addSuperVisorBtn);
        addSuperVisor.setOnClickListener(view -> addSuperVisor());

    }

    /**
     * Method for starting the info activity
     */
    private void infoBtnClick() {
        Intent info = new Intent(this, Info.class);
        startActivity(info);
    }

    /**
     * Method that checks employeeID and password from database and logIn to respective activity for employee and employer based on roles mentioned on the database
     * If the employee is inactive, employee won't be able to log in.
     */

    private void LoginBtnClick() {
        String employeeId = ((EditText) findViewById(R.id.userIdText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        TextView errorTextView = findViewById(R.id.errorText);

        //checking if the input matches with employee's employee ID and password
        Employee employee = database.employeeDao().checkLogIn(employeeId, password);

        if (employee != null) {
            //If else statement for comparing role and status of employer and employees
            if (employee.getRole().equals(getString(R.string.esimies)) && employee.getStatus().equals(getString(R.string.active))) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent employerActivity = new Intent(this, EmployerActivity.class);
                String message = getString(R.string.welcomeEmployeeDetails, employee.getName(), employee.getEmployeeId());
                employerActivity.putExtra(EMPLOYER_LOGIN, message);
                employerActivity.putExtra(EMPLOYER_ID, employee.getEmployeeId());
                startActivity(employerActivity);
            } else if (employee.getRole().equals(getString(R.string.worker)) && employee.getStatus().equals(getString(R.string.active))) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent employeeActivity = new Intent(this, EmployeeWindow.class);
                String message = getString(R.string.welcomeEmployeeDetails, employee.getName(), employee.getEmployeeId());
                employeeActivity.putExtra(EMPLOYEE_LOGIN, message);
                employeeActivity.putExtra(EMPLOYEE_ID, employee.getEmployeeId());
                startActivity(employeeActivity);
            } else {
                Toast.makeText(this, "User Inactive !!! ", Toast.LENGTH_SHORT).show();
                errorTextView.setText(R.string.userInactive);

            }

        } else {
            Toast.makeText(this, "Wrong Credentials !!! ", Toast.LENGTH_SHORT).show();
            errorTextView.setText(R.string.wrongCredentials);

        }

    }

    /**
     * Method for adding new supervisor/ manager in the database
     */
    private void addSuperVisor() {
        Intent intent = new Intent(this, AddSuperVisor.class);
        startActivity(intent);
    }

    /**
     * onResume methods resets all the user inputs when the activity is resumed
     */
    protected void onResume() {
        super.onResume();
        EditText idText = findViewById(R.id.userIdText);
        EditText password = findViewById(R.id.passwordText);
        TextView errorText = findViewById(R.id.errorText);
        idText.setText("");
        password.setText("");
        errorText.setText("");
    }

    /**
     * Method for not letting user to input an empty string
     */
    private void emptyFieldCheck() {
        String employeeId = ((EditText) findViewById(R.id.userIdText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        TextView errorTextView = findViewById(R.id.errorText);
        //If-else statement for checking if the input is empty
        if (employeeId.equals("") || password.equals("")) {
            errorTextView.setText(R.string.empty_field_message);
        } else {
            LoginBtnClick();
        }
    }

    /**
     * Disable Physical back button
     * Avoids chances of moving back to signed it status after signOut button is Pressed
     * @see <a href="https://developer.android.com/reference/android/widget/Toast">Toast</a>
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Button Disabled", Toast.LENGTH_SHORT).show();

    }
}