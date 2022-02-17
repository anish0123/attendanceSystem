package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

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
        addButton.setOnClickListener(view -> addButtonClick());

        //Introducing listview for displaying all the employees
        ListView lvList = findViewById(R.id.lvEmployee);
        lvList.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                database.employeeDao().getAll()
        ));


    }

    //Method for adding the employees.
    public void addButtonClick() {
        String newEmployeeName = ((EditText) findViewById(R.id.etEmployeeName)).getText().toString();
        String newEmployeePassword = ((EditText) findViewById(R.id.etPassword)).getText().toString();
        String newEmployeeId =((EditText) findViewById(R.id.etEmployeeId)).getText().toString();
        Long id = database.employeeDao().insert(new Employee(0, newEmployeeId, newEmployeeName, newEmployeePassword, "worker"));
    }
}