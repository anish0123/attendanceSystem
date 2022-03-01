package fi.metropolia.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.DisplayMetrics;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //making window as pop up window
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight= displayMetrics.heightPixels;

        getWindow().setLayout((int)(screenWidth),(int)(screenHeight*.7));
    }
}