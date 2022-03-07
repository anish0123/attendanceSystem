package fi.metropolia.attendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;

/**
 * This class is created for the activity where the employer can add employees in the database and see the list of employees.
 */
public class EmployerActivity extends AppCompatActivity {
    //String defined for putting it as extra in intent
    public static final String EMPLOYEE_ID = "employee_ID";
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        //Getting the intent from the main activity
        Intent intent = getIntent();
        String welcomeMessage = intent.getStringExtra(MainActivity.EMPLOYER_LOGIN);
        //Displaying the employer details in the textView
        TextView welcome = findViewById(R.id.welcomeBoss);
        welcome.setText(welcomeMessage);

        // Introducing database and image button which will be used to add employees in the database.
        database = AppDataBase.getInstance(getApplicationContext());
        ImageButton addButton = findViewById(R.id.addBtn);

        //Logout button introduced to take employer to main activity after pressing the logout button.
        Button signOut = findViewById(R.id.logOutBtn);
        signOut.setOnClickListener(view -> backToMain());
        addButton.setOnClickListener(view -> checkRedundancy());

        updateUI();
    }

    /**
     * Method for adding the employees in the database.
     * It takes input from user for employee's name, password and Id.
     * If empty string is inserted, it shows error.
     */
    private void addButtonClick() {
        String newEmployeeName = ((EditText) findViewById(R.id.etEmployeeName)).getText().toString();
        String newEmployeePassword = ((EditText) findViewById(R.id.etPassword)).getText().toString();
        String newEmployeeId = ((EditText) findViewById(R.id.etEmployeeId)).getText().toString();
        //If else statement introduced so that user is not able to input empty string in the editTexts
        if (newEmployeeName.equals("") || newEmployeePassword.equals("") || newEmployeeId.equals("")) {
            Toast.makeText(this, "Required fields empty ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, newEmployeeName + " added ", Toast.LENGTH_SHORT).show();
            database.employeeDao().insert(new Employee(newEmployeeId, newEmployeeName, newEmployeePassword, getString(R.string.worker), getString(R.string.active)));
            updateUI();
        }
    }


    /**
     * Method for checking if employeeId is already assigned to avoid same id for employees
     * If the employeeId is already assigned, it will display toast with text: Employee Id exists.
     */
    private void checkRedundancy() {
        String newEmployeeId = ((EditText) findViewById(R.id.etEmployeeId)).getText().toString();
        Employee employee = database.employeeDao().getByEmployeeId(newEmployeeId);
        //If-else statement for checking if the employee id already exists
        if (employee != null) {
            Toast.makeText(this, "Employee Id exists", Toast.LENGTH_SHORT).show();
        } else {
            addButtonClick();
        }

    }

    /**
     * Method for updating the user interface.
     */
    private void updateUI() {
        //Introducing listview for displaying all the employees
        ListView lvList = findViewById(R.id.lvEmployee);
        //Displaying the list of all the employees on the listView
        lvList.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                database.employeeDao().getAll()
        ));
        //setup the click listener so when employee name is clicked the work history opens up.
        lvList.setOnItemClickListener((adapterView, view, i, l) -> startActivityEmployeeHistory(((Employee) lvList.getItemAtPosition(i)).getEmployeeId()));

    }

    /**
     * Method for opening employerViewHistory activity according to which employee has been clicked in the listView.
     *
     * @param employeeId Employee's Id
     */
    private void startActivityEmployeeHistory(String employeeId) {
        Intent intent = new Intent(EmployerActivity.this, EmployerViewHistory.class);
        intent.putExtra(EMPLOYEE_ID, employeeId);
        startActivity(intent);

    }

    /**
     * Method for going back to the main activity.
     */
    private void backToMain() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    /**
     * Disable Physical back button
     * Avoids chances of moving back to signed it status after signOut button is Pressed
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Button Disabled", Toast.LENGTH_SHORT).show();

    }

    /**
     * Updating the UI when user comes back to this activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
}

