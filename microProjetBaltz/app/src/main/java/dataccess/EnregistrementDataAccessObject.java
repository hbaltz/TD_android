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
import dataobjects.enreg_info;

/**
 * Created by hbaltz on 15/02/2016.
 */


public class EnregistrementDataAccessObject {

    //cette classe sert à faire le lien entre les actions sur les objets et la table de la base de données (insert, update, delete)

    public static final String ID="ID";
    public static final String LONG="LONG";
    public static final String LAT="LAT";
    public static final String DESC="DESC";
    public static final String TYPE="TYPE";
    public static final String PHOTO="PHOTO";
    public static final String TABLE_NAME="ENREG_INFO";

    private final DataSource datasource;

    //constructor
    public EnregistrementDataAccessObject(DataSource datasource){
        this.datasource = datasource;
    }

    public synchronized enreg_info create(enreg_info objet) throws SQLException {
        //on copie les champs de l'objet dans les colonnes de la table.
        ContentValues values=new ContentValues();

        //values.put(COL_ID, objet.getId()); //Ne pas fixé l'ID. c'est automatique avec SQLIte.
        values.put(LONG,objet.getLong());
        values.put(LAT,objet.getLat());
        values.put(DESC,objet.getDesc());
        values.put(TYPE,objet.getType());
        values.put(PHOTO,objet.getPhoto());

        //insert query
        int id=(int)datasource.getDB().insert(TABLE_NAME,null,values);

        //mise à jour de l'ID dans l'objet
        objet.setId(id);

        return objet;
    }

    public synchronized enreg_info update(enreg_info objet){
        //on copie les champs de l'objet dans les colonnes de la table.
        ContentValues values=new ContentValues();
        values.put(ID, objet.getId());
        values.put(LONG,objet.getLong());
        values.put(LAT,objet.getLat());
        values.put(DESC,objet.getDesc());
        values.put(TYPE,objet.getType());
        values.put(PHOTO,objet.getPhoto());

        //gestion de la clause "WHERE"
        String clause = ID + " = ? ";
        String[] clauseArgs = new String[]{
                String.valueOf(objet.getId())
        };

        datasource.getDB().update(TABLE_NAME, values, clause, clauseArgs);

        //mise à jour de l'ID dans l'objet
        return objet;
    }

    public synchronized void delete(enreg_info objet){

        //gestion de la clause "WHERE"
        String clause = ID + " = ? ";
        String[] clauseArgs = new String[]{
                String.valueOf(objet.getId())
        };
        datasource.getDB().delete(TABLE_NAME, clause, clauseArgs);
    }

    public enreg_info read(enreg_info objet){

        //columns
        String[] allColumns = new String[]{ID,LONG,LAT,DESC,TYPE,PHOTO};

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
        objet.setLong(cursor.getInt(1));
        objet.setLat(cursor.getInt(2));
        objet.setDesc(cursor.getString(3));
        objet.setType(cursor.getString(4));
        objet.setPhoto(cursor.getString(5));
        cursor.close();

        return objet;
    }

    public List<enreg_info> readAll(){

        //columns
        String[] allColumns = new String[]{ID,LONG,LAT,DESC,TYPE,PHOTO};

        //select query
        Cursor cursor = datasource.getDB().query(TABLE_NAME,allColumns, null, null, null, null,null);

        //Iterate on cursor and retrieve result
        List<enreg_info> liste_enregistrement = new ArrayList<enreg_info>();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            liste_enregistrement.add(new enreg_info(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            cursor.moveToNext();
        }

        cursor.close();

        return liste_enregistrement;
    }

    public ArrayList<Integer> getLatLng(){

        //columns
        String[] Columns = new String[]{LONG,LAT};

        //select query
        Cursor cursor = datasource.getDB().query(TABLE_NAME,Columns, null, null, null, null,null);

        //Iterate on cursor and retrieve result
        ArrayList<Integer> liste_enregistrement = new ArrayList<Integer>();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            liste_enregistrement.add(cursor.getInt(0));
            liste_enregistrement.add(cursor.getInt(1));
            cursor.moveToNext();
        }

        cursor.close();

        return liste_enregistrement;
    }
}