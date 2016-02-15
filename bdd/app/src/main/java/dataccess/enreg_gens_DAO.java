package dataccess;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import dataobjects.enreg_gens;

/**
 * Created by hbaltz on 10/02/2016.
 */

public class enreg_gens_DAO {

    public static final String TABLE_NAME = "ENREG_GENS";
    public static final String KEY = "ID";
    public static final String NOM = "NOM";
    public static final String PRENOM = "PRENOM";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM + " TEXT, " + PRENOM + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
