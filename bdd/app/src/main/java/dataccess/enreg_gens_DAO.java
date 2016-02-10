package dataccess;

/**
 * Created by formation on 10/02/2016.
 */

public class enreg_gens_DAO {
    public static final String TABLE_NAME = "enreg_gens";
    public static final String KEY = "id";
    public static final String NOM = "Nom";
    public static final String PRENOM = "Prenom";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM + " TEXT, " + PRENOM + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    
}
