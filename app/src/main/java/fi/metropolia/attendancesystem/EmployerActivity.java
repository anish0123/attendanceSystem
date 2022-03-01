package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;
import fi.metropolia.attendancesystem.database.EmployeeAttendance;

public class EmployerActivity extends AppCompatActivity {
    public static final String EMPLOYEE_ID = "employee_ID";

    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);

        //Logout button introduced to take employer to main activity after pressing the logout button.
        Button signOut = findViewById(R.id.logOutBtn);
        signOut.setOnClickListener(view -> backToMain());

        // Introducing database and image button which will be used to add employees in the database.
        database = AppDataBase.getInstance(getApplicationContext());
        ImageButton addButton = findViewById(R.id.addBtn);
        ImageButton removeButton = findViewById(R.id.removeButton);

        addButton.setOnClickListener(view -> checkRedundancy());
        removeButton.setOnClickListener(view -> removeButtonClick());

        updateUI();


    }

    //Method for adding the employees.
    public void addButtonClick() {
        String newEmployeeName = ((EditText) findViewById(R.id.etEmployeeName)).getText().toString();
        String newEmployeePassword = ((EditText) findViewById(R.id.etPassword)).getText().toString();
        String newEmployeeId = ((EditText) findViewById(R.id.etEmployeeId)).getText().toString();
        Toast.makeText(this, newEmployeeName+" added ", Toast.LENGTH_SHORT).show();
        database.employeeDao().insert(new Employee(newEmployeeId, newEmployeeName, newEmployeePassword, getString(R.string.worker),getString(R.string.active)));
        updateUI();
    }

    public void removeButtonClick() {
        String employeeId = ((EditText) findViewById(R.id.removeEmployee)).getText().toString();
        Toast.makeText(this," Employee Removed ", Toast.LENGTH_SHORT).show();
        database.employeeDao().updateEmployee(employeeId,"inactive");
        updateUI();

    }

    //Method for checking if employeeid is already assigned to avoid same id for employees
    public void checkRedundancy() {
        String newEmployeeId = ((EditText) findViewById(R.id.etEmployeeId)).getText().toString();

        Employee employee = database.employeeDao().getByEmployeeId(newEmployeeId);
        if (employee != null) {
            Toast.makeText(this, "Employee Id exists", Toast.LENGTH_SHORT).show();
        } else {
            addButtonClick();
        }

    }

    public void updateUI() {
        //Introducing listview for displaying all the employees
        ListView lvList = findViewById(R.id.lvEmployee);
        lvList.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                database.employeeDao().getAll()
        ));

        //setup the click listener so when employee name is clicked the work history opens up.
        lvList.setOnItemClickListener(((adapterView, view, i, l) -> {
            startActivityEmployeeHistory(i);
        }));

    }

    private void startActivityEmployeeHistory(int i) {
        Intent intent = new Intent(EmployerActivity.this, EmployerViewHistoryActivity.class);
        List list = database.employeeDao().getAll();
        Employee employee = (Employee) list.get(i);
        String employeeId = employee.getEmployeeId();
       intent.putExtra(EMPLOYEE_ID,employeeId);
       startActivity(intent);

    }
    public void backToMain(){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
    }
}
