package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;

public class EmployerActivity extends AppCompatActivity {
    public static final String EMPLOYEE_ID = "employee_ID";
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        Intent intent = getIntent();
        String welcomeMessage = intent.getStringExtra(MainActivity.EMPLOYER_LOGIN);

        TextView welcome = findViewById(R.id.welcomeBoss);
        welcome.setText(welcomeMessage);

        // Introducing database and image button which will be used to add employees in the database.
        database = AppDataBase.getInstance(getApplicationContext());
        ImageButton addButton = findViewById(R.id.addBtn);
        ImageButton removeButton = findViewById(R.id.removeButton);
        ImageButton enableButton = findViewById(R.id.enableEmployeeButton);

        //Logout button introduced to take employer to main activity after pressing the logout button.
        Button signOut = findViewById(R.id.logOutBtn);
        signOut.setOnClickListener(view -> backToMain());
        addButton.setOnClickListener(view -> checkRedundancy());
        removeButton.setOnClickListener(view -> removeButtonClick());
        enableButton.setOnClickListener(view -> enableButtonClick());

        updateUI();
    }

    /**
     * Method for adding the employees in the database.
     * It takes input from user for employee's name, password and Id.
     */
    public void addButtonClick() {
        String newEmployeeName = ((EditText) findViewById(R.id.etEmployeeName)).getText().toString();
        String newEmployeePassword = ((EditText) findViewById(R.id.etPassword)).getText().toString();
        String newEmployeeId = ((EditText) findViewById(R.id.etEmployeeId)).getText().toString();
        Toast.makeText(this, newEmployeeName+" added ", Toast.LENGTH_SHORT).show();
        database.employeeDao().insert(new Employee(newEmployeeId, newEmployeeName, newEmployeePassword, getString(R.string.worker),getString(R.string.active)));
        updateUI();
    }

    /**
     * Method for making the employees inactive.
     * When the employee is inactive, they won't be able to log in into their account but managers will still be check employee's previous history.
     */
    public void removeButtonClick() {
        String inactive = "inactive";
        String employeeId = ((EditText) findViewById(R.id.removeEmployee)).getText().toString();
        Toast.makeText(this," Employee Removed ", Toast.LENGTH_SHORT).show();
        database.employeeDao().updateEmployee(employeeId,inactive);
        updateUI();

    }
    public void enableButtonClick() {
        String active = "active";
        String employeeId = ((EditText) findViewById(R.id.removeEmployee)).getText().toString();
        Toast.makeText(this," Employee Status back to Active ", Toast.LENGTH_SHORT).show();
        database.employeeDao().updateEmployee(employeeId,active);
        updateUI();

    }

    /**
     * Method for checking if employeeId is already assigned to avoid same id for employees
     * If the employeeId is already assigned, it will display toast with text: Employee Id exists.
     */
    public void checkRedundancy() {
        String newEmployeeId = ((EditText) findViewById(R.id.etEmployeeId)).getText().toString();

        Employee employee = database.employeeDao().getByEmployeeId(newEmployeeId);
        if (employee != null) {
            Toast.makeText(this, "Employee Id exists", Toast.LENGTH_SHORT).show();
        } else {
            addButtonClick();
        }

    }

    /**
     * Method for updating the user interface.
     */
    public void updateUI() {
        //Introducing listview for displaying all the employees
        ListView lvList = findViewById(R.id.lvEmployee);
        lvList.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                database.employeeDao().getAll()
        ));

        //setup the click listener so when employee name is clicked the work history opens up.
        lvList.setOnItemClickListener(((adapterView, view, i, l) -> startActivityEmployeeHistory(i)));

    }

    /**
     * Method for opening employerViewHistory activity according to which employee has been clicked in the listView.
     * @param i the number for the clicked employee from the listView.
     */
    private void startActivityEmployeeHistory(int i) {
        Intent intent = new Intent(EmployerActivity.this, EmployerViewHistory.class);
        List<Employee> list = database.employeeDao().getAll();
        Employee employee = list.get(i);
        String employeeId = employee.getEmployeeId();
        intent.putExtra(EMPLOYEE_ID,employeeId);
        startActivity(intent);

    }

    /**
     * Method for going back to the main activity.
     */
    public void backToMain(){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
    }
}
