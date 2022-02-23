package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EmployerEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_edit);
    }

    public void backToMain(){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
    }
}