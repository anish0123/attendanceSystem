package fi.metropolia.attendancesystem;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Employee.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public static final String TAG = "Database";

    public abstract EmployeeInterface employeeInterface();

    private static final String DB_NAME = "app_database.db";
    private static AppDataBase instance;

    public static synchronized AppDataBase getInstance(Context context) {
        if(null == instance) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDataBase create(Context context) {
        Log.d(TAG, "Only one instance created?");
        return Room.databaseBuilder(context, AppDataBase.class, DB_NAME)
                //normally you should run in background, avoid to freeze UI and get killed by OS.
                .allowMainThreadQueries()
                .build();

    }

}
