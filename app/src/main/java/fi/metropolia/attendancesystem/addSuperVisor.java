package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;

public class addSuperVisor extends AppCompatActivity {
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supervisor);


        database = AppDataBase.getInstance(getApplicationContext());

        //assign imageButton Id and its click listener
        ImageButton addSuperVisor = findViewById(R.id.addSuperVisorButton);
        addSuperVisor.setOnClickListener(view -> checkRedundancy());

    }

    //add manager function to add superVisor and has a uniqueToken
    public void addButtonClick() {
        String newEmployeeName = ((EditText) findViewById(R.id.superVisorName)).getText().toString();
        String newEmployeePassword = ((EditText) findViewById(R.id.superVisorPassword)).getText().toString();
        String newEmployeeId = ((EditText) findViewById(R.id.superVisorId)).getText().toString();
        String uniqueToken = ((EditText) findViewById(R.id.uniqueToken)).getText().toString();
        if (uniqueToken.equals("112233")) {
            Toast.makeText(this, "SuperVisor added", Toast.LENGTH_SHORT).show();
            database.employeeDao().insert(new Employee(newEmployeeId, newEmployeeName, newEmployeePassword, "esimies", "active"));
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();

        }
    }

    //Method for checking if employeeid is already assigned to avoid same id for either employer or employees
    public void checkRedundancy() {
        String newEmployeeId = ((EditText) findViewById(R.id.superVisorId)).getText().toString();

        Employee employee = database.employeeDao().getByEmployeeId(newEmployeeId);
        if (employee != null) {
            Toast.makeText(this, "Employee Id exists", Toast.LENGTH_SHORT).show();
        } else {
            addButtonClick();
        }

    }
}