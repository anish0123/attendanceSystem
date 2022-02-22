package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fi.metropolia.attendancesystem.database.AppDataBase;

public class Employee_history extends AppCompatActivity {
        private AppDataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_history);
        dataBase=AppDataBase.getInstance(getApplicationContext());
        historyUI();
    }
    public void historyUI(){
        ListView listView=findViewById(R.id.historyList);
        listView.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dataBase.attendanceDao().getAllAttendance("001")
        ));


    }
}