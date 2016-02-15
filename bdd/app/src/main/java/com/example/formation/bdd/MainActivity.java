package com.example.formation.bdd;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import dataccess.enreg_gens_DAO;
import dataccess.DataSource;
import dataccess.EnregistrementDataAccessObject;
import dataobjects.enreg_gens;

public class MainActivity extends AppCompatActivity {

    //variables pour la gestion de la base de données SQLite
    DataSource mdatasource = null;
    EnregistrementDataAccessObject mEnregistrementDataAccessObject=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instanciation de la base de données SQLite
        mdatasource = new DataSource(this);
        mEnregistrementDataAccessObject = mdatasource.newEnregistrementhDataAccessObject();
        mdatasource.open();

        enreg_gens mEnregistrement1=new enreg_gens(-1, this.champ1, this.champ2); //L'identifiant "-1" dit à SQLite de créer un nouvel identifiant en autoincrémentation avec ORMlite. Inutile avec SQlite.

        //stockage des attributs de l'objet dans la base de données
        mEnregistrementDataAccessObject.create(mEnregistrement1);


        //controle de l'écriture dans la base de données
        List<enreg_gens> liste_enregistrement = mEnregistrementDataAccessObject.readAll();
        for (enreg_gens mEnregistrement : liste_enregistrement){
            Log.w("CONTROLE", mEnregistrement.toString() + "\n");
        }


    }
}
