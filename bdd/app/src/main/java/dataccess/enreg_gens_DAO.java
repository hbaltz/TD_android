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

public class DBHelper extends SQLiteOpenHelper {

    //utilisation du modèle de données
    public static final String DB_NAME = "/mnt/sdcard/DB_ING2.db";
    public static final int DB_VERSION = 1;

    //constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static String getQueryCreate() {
        String requete = "";
        requete = "CREATE TABLE ENREG_GENS("
                + "ID Integer PRIMARY KEY AUTOINCREMENT, "
                + "NOM Text, "
                + "PRENOM Text"
                + ");";
        return requete;
    }

    public static String getQueryDrop() {
        return "DROP TABLE IF EXISTS ENREG_GENS;";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //ceci est automatiquement géré par SQLite
        db.execSQL(getQueryCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getQueryDrop());
        db.execSQL(getQueryCreate());
    }
}

public class DataSource {
    //connexion à la base de données

    private SQLiteDatabase db;
    private final DBHelper helper;

    public DataSource(Context context){
        helper = new DBHelper(context);
    }

    public SQLiteDatabase getDB() throws SQLException{
        if (db == null) open ();
        return db;
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    //factory
    public enreg_gens_DAO newEnregistrementhDataAccessObject(){
        return new enreg_gens_DAO(this);
    }
}

public class enreg_gens_DAO {

    public static final String TABLE_NAME = "ENREG_GENS";
    public static final String KEY = "ID";
    public static final String NOM = "NOM";
    public static final String PRENOM = "PRENOM";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM + " TEXT, " + PRENOM + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

}
