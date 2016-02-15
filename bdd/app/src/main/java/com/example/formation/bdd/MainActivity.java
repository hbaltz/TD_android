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
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import dataccess.enreg_gens_DAO;
import dataccess.DataSource;
import dataccess.EnregistrementDataAccessObject;
import dataobjects.enreg_gens;

public class MainActivity extends AppCompatActivity {

    // Déclaration des variables pour les évenements:
    private EditText editNom = null;
    private EditText editPrenom = null;

    private TextView TNom = null;
    private TextView TPrenom = null;

    private String nom = null;
    private String prenom = null;

    //variables pour la gestion de la base de données SQLite
    DataSource mdatasource = null;
    EnregistrementDataAccessObject mEnregistrementDataAccessObject=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperation des ressources :
        nom = getResources().getString(R.string.Nom);
        prenom = getResources().getString(R.string.Prenom);

        // Recuperation des views :
        TNom = (TextView)findViewById(R.id.TNom);
        editNom = (EditText)findViewById(R.id.editNom);
        TPrenom = (TextView)findViewById(R.id.TPrenom);
        editPrenom = (EditText)findViewById(R.id.editPrenom);

        // Remplissage des zones de texte :
        TNom.setText(nom);
        editNom.setHint(nom);
        TPrenom.setText(prenom);
        editPrenom.setHint(prenom);

        //instanciation de la base de données SQLite
        mdatasource = new DataSource(this);
        mEnregistrementDataAccessObject = mdatasource.newEnregistrementhDataAccessObject();
        mdatasource.open();

        //L'identifiant "-1" dit à SQLite de créer un nouvel identifiant en autoincrémentation avec ORMlite. Inutile avec SQlite.
        enreg_gens mEnregistrement1 = new enreg_gens(-1, this.champ1, this.champ2);

        //stockage des attributs de l'objet dans la base de données
        mEnregistrementDataAccessObject.create(mEnregistrement1);


        //controle de l'écriture dans la base de données
        List<enreg_gens> liste_enregistrement = mEnregistrementDataAccessObject.readAll();
        for (enreg_gens mEnregistrement : liste_enregistrement){
            Log.w("CONTROLE", mEnregistrement.toString() + "\n");
        }


    }
}
