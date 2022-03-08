package fi.metropolia.attendancesystem;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class is created for the activity where employee can check for info about how to use the app
 */

public class Info extends AppCompatActivity {

    /**
     * Popup window is created in this onCreate method
     * @see <a href="https://www.youtube.com/watch?v=fn5OlqQuOCk">Popup Window</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //making window as pop up window
        //Credit: https://www.youtube.com/watch?v=fn5OlqQuOCk
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        getWindow().setLayout(screenWidth, (int) (screenHeight * .7));
    }
}