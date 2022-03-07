package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;

/**
 * This class is created for the activity to add supervisor/manager in the database and application.
 */
public class AddSuperVisor extends AppCompatActivity {
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supervisor);
        //Introduced database
        database = AppDataBase.getInstance(getApplicationContext());

        //assign imageButton Id and its click listener
        ImageButton addSuperVisor = findViewById(R.id.addSuperVisorButton);
        addSuperVisor.setOnClickListener(view -> checkRedundancy());

    }

    /**
     * Method to add superVisor and has a uniqueToken which only allows user to create new supervisor/manager
     */
    private void addButtonClick() {
        String newEmployeeName = ((EditText) findViewById(R.id.superVisorName)).getText().toString();
        String newEmployeePassword = ((EditText) findViewById(R.id.superVisorPassword)).getText().toString();
        String newEmployeeId = ((EditText) findViewById(R.id.superVisorId)).getText().toString();
        String uniqueToken = ((EditText) findViewById(R.id.uniqueToken)).getText().toString();
        //If-else statement for checking mull input and adding supervisor if the unique token matches
        if (newEmployeeId.equals("") || newEmployeeName.equals("") || newEmployeePassword.equals("")) {
            Toast.makeText(this, "Required fields empty", Toast.LENGTH_SHORT).show();
        } else {
            if (uniqueToken.equals(getString(R.string.uniqueKey))) {
                Toast.makeText(this, "SuperVisor added", Toast.LENGTH_SHORT).show();
                database.employeeDao().insert(new Employee(newEmployeeId, newEmployeeName, newEmployeePassword, getString(R.string.esimies), getString(R.string.active)));
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * Method for checking if employeeId is already assigned to avoid same id for employers
     */
    private void checkRedundancy() {
        String newEmployeeId = ((EditText) findViewById(R.id.superVisorId)).getText().toString();

        Employee employee = database.employeeDao().getByEmployeeId(newEmployeeId);
        //If-else statement for checking if the employeeId already exists or not
        if (employee != null) {
            Toast.makeText(this, "Employee Id exists", Toast.LENGTH_SHORT).show();
        } else {
            addButtonClick();
        }

    }
}