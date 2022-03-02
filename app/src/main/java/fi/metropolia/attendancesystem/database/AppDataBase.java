package fi.metropolia.attendancesystem.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * This class is created for creating database and storing the details of database
 */
@Database(entities = {Employee.class,EmployeeAttendance.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public static final String TAG = "DATABASE";

    public abstract EmployeeDao employeeDao();
    public abstract AttendanceDao attendanceDao();
    private static final String DB_NAME = "app_database.db";
    private static AppDataBase instance;

    /**
     * Method for getting the instance of the AppDataBase class
     * @param context context
     * @return instance of the database
     */
    public static synchronized AppDataBase getInstance(Context context) {
        if(null==instance) {
            instance =create(context);
        }
        return instance;
    }

    /**
     * Method for creating the database
     * @param context context
     * @return room database
     */
    private  static AppDataBase create(Context context) {
        Log.d(TAG, "only one instance created?");
        return Room.databaseBuilder(context, AppDataBase.class, DB_NAME)
                //for letting app to run in background
                .allowMainThreadQueries()
                .build();
    }


}
