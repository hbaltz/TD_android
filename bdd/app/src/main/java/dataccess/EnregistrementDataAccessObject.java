package dataccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dataccess.DataSource;
import dataobjects.enreg_gens;

/**
 * Created by hbaltz on 15/02/2016.
 */


public class EnregistrementDataAccessObject {

    //cette classe sert à faire le lien entre les actions sur les objets et la table de la base de données (insert, update, delete)

    public static final String ID="id";
    public static final String NOM="Nom";
    public static final String PRENOM="Prenom";
    public static final String TABLE_NAME="ENREG_GENS";

    private final DataSource datasource;

    //constructor
    public EnregistrementDataAccessObject(DataSource datasource){
        this.datasource = datasource;
    }

    public synchronized enreg_gens create(enreg_gens objet) throws SQLException {
        //on copie les champs de l'objet dans les colonnes de la table.
        ContentValues values=new ContentValues();

        //values.put(COL_ID, objet.getId()); //Ne pas fixé l'ID. c'est automatique avec SQLIte.
        values.put(NOM,objet.getNom());
        values.put(PRENOM,objet.getPrenom());

        //insert query
        int id=(int)datasource.getDB().insert(TABLE_NAME,null,values);

              //mise à jour de l'ID dans l'objet
        objet.setId(id);

        return objet;

    }

    public synchronized enreg_gens update(enreg_gens objet){

        //on copie les champs de l'objet dans les colonnes de la table.
        ContentValues values=new ContentValues();
        values.put(ID, objet.getId());
        values.put(NOM,objet.getNom());
        values.put(PRENOM,objet.getPrenom());

        //gestion de la clause "WHERE"
        String clause = ID + " = ? ";
        String[] clauseArgs = new String[]{
                String.valueOf(objet.getId())
        };

        datasource.getDB().update(TABLE_NAME, values, clause, clauseArgs);

        //mise à jour de l'ID dans l'objet
        return objet;
    }

    public synchronized void delete(enreg_gens objet){

        //gestion de la clause "WHERE"
        String clause = ID + " = ? ";
        String[] clauseArgs = new String[]{
                String.valueOf(objet.getId())
        };
        datasource.getDB().delete(TABLE_NAME, clause, clauseArgs);
    }

    public enreg_gens read(enreg_gens objet){

        //columns
        String[] allColumns = new String[]{ID,NOM,PRENOM};

        //clause
        String clause = ID + " = ? ";
        String[] clauseArgs = new String[]{
                String.valueOf(objet.getId())
        };

        //select query
        Cursor cursor = datasource.getDB().query(TABLE_NAME,allColumns, "Id = ?", clauseArgs, null, null,null);

        //read cursor. On copie les valeurs de la table dans l'objet
        cursor.moveToFirst();
        objet.setId(cursor.getInt(0));
        objet.setNom(cursor.getString(1));
        objet.setPrenom(cursor.getString(2));
        cursor.close();

        return objet;
    }

    public List<enreg_gens> readAll(){

        //columns
        String[] allColumns = new String[]{ID,NOM,PRENOM};

        //select query
        Cursor cursor = datasource.getDB().query(TABLE_NAME,allColumns, null, null, null, null,null);

        //Iterate on cursor and retrieve result
        List<enreg_gens> liste_enregistrement = new ArrayList<enreg_gens>();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            liste_enregistrement.add(new enreg_gens(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }

        cursor.close();

        return liste_enregistrement;
    }
}