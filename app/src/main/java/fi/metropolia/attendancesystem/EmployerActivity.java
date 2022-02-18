package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;

public class EmployerActivity extends AppCompatActivity {

    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);

        //Logout button introduced to take employer to main activity afer pressing the logout button.
        Button signOut = findViewById(R.id.logOutBtn);
        signOut.setOnClickListener(view -> finish());

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
        Long id = database.employeeDao().insert(new Employee(0, newEmployeeId, newEmployeeName, newEmployeePassword, "worker"));
        updateUI();
    }

    public void removeButtonClick(){
        String employeeId = ((EditText) findViewById(R.id.removeEmployee)).getText().toString();
        database.employeeDao().deleteEmployee(employeeId);
        updateUI();

    }
    //Method for checking if employeeid is already assigned to avoid same id for employees
    public  void checkRedundancy(){
        String newEmployeeId = ((EditText) findViewById(R.id.etEmployeeId)).getText().toString();

        List<Employee> employeeList = database.employeeDao().getAll();
        Boolean employeeExists = false;
        for (Employee workingEmployee : employeeList) {
            if(newEmployeeId.equals(workingEmployee.getEmployeeId())){
                employeeExists = true;
                Toast.makeText(this, "Employee Id exists", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(employeeExists==false){
            addButtonClick();
        }
    }

    public void updateUI(){
        //Introducing listview for displaying all the employees
        ListView lvList = findViewById(R.id.lvEmployee);
        lvList.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                database.employeeDao().getAll()
        ));


    }
}