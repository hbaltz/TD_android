package dataccess;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hbaltz on 15/02/2016.
 */

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
    public EnregistrementDataAccessObject newEnregistrementhDataAccessObject(){
        return new EnregistrementDataAccessObject(this);
    }
}
