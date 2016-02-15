package dataccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hbaltz on 15/02/2016.
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