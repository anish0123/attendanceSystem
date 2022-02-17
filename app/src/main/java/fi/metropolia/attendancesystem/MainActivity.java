package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import fi.metropolia.attendancesystem.database.AppDataBase;
import fi.metropolia.attendancesystem.database.Employee;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "checking main activity";
    private AppDataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        login();
    }

    public void login() {

        //assigning id for edittext,errorTextView and login button
        EditText employeeId = findViewById(R.id.userIdText);
        EditText password = findViewById(R.id.passwordText);
        TextView errorTextView = findViewById(R.id.errorText);
        ImageButton signInBtn = findViewById(R.id.logInButton);


        database = AppDataBase.getInstance(getApplicationContext());

        Employee employee = new Employee(0,"000", "Argie", "argie123","esimies" );




        long id = database.employeeDao().insert(employee);

        //check existing esimies and avoid multiple entries
        List<Employee> employeeList = database.employeeDao().getAll();
        Log.d(TAG, "is it getting the employee list");
        for (Employee employee1 : employeeList) {
            Log.d(TAG, "is it inside the for loop?");
            if (employee1.getColumnId() > 1 && employee1.getRole().equals("esimies")) {
                Log.d(TAG, "is it trying to delete?");
               database.employeeDao().delete(employee);
            }

        }



    }

}