package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import fi.metropolia.attendancesystem.database.AppDataBase;

public class Employee_history extends AppCompatActivity {
        private AppDataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_history);
        dataBase=AppDataBase.getInstance(getApplicationContext());
        historyUI();
        Button signOutButton = findViewById(R.id.signOut2);
        signOutButton.setOnClickListener(view -> backToMain());
    }
    public void historyUI(){
        TextView textView = findViewById(R.id.employeeHistoryId);
        Intent intent = getIntent();
        String employeeId = intent.getStringExtra(employeeWindow.EMPLOYEE_ID_SEND);
        String employeeDetail = intent.getStringExtra(employeeWindow.EMPLOYEE_DETAIL);
        textView.setText(employeeDetail);
        ListView listView=findViewById(R.id.historyList);
        listView.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dataBase.attendanceDao().getAllAttendance(employeeId)
        ));
    }
    public void backToMain(){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
    }
}