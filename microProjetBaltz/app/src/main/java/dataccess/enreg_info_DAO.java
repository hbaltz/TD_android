package dataccess;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import dataobjects.enreg_info;

/**
 * Created by hbaltz on 10/02/2016.
 */

public class enreg_info_DAO {

    public static final String TABLE_NAME = "ENREG_INFO";
    public static final String KEY = "ID";
    public static final String LONG = "LONG";
    public static final String LAT = "LAT";
    public static final String DESC="DESC";
    public static final String TYPE="TYPE";
    public static final String PHOTO="PHOTO";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LONG + " FLOAT, "
            + LAT + " FLOAT, "
            + DESC + " TEXT, "
            + TYPE + " TEXT, "
            + PHOTO +" TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
