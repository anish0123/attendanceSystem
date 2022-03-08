package fi.metropolia.attendancesystem.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * This class is created for creating database and storing the details of database
 */
@Database(entities = {Employee.class, EmployeeAttendance.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    //Defining fields of the class

    /**
     * Interface that will connect employee table with activities
     *
     * @return employee
     */
    public abstract EmployeeDao employeeDao();

    /**
     * Interface that will connect employeeAttendance table with activities
     *
     * @return employeeAttendance
     */
    public abstract AttendanceDao attendanceDao();

    /**
     * name for the database as an String
     */
    private static final String DB_NAME = "app_database.db";

    /**
     * Instance of the database
     */
    private static AppDataBase instance;

    /**
     * Method for getting the instance of the AppDataBase class
     *
     * @param context context
     * @return instance of the database
     */
    public static synchronized AppDataBase getInstance(Context context) {
        if (null == instance) {
            instance = create(context);
        }
        return instance;
    }

    /**
     * Method for creating the database
     *
     * @param context context
     * @return room database
     */
    private static AppDataBase create(Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, DB_NAME)
                //for letting app to run in background
                .allowMainThreadQueries()
                .build();
    }


}
