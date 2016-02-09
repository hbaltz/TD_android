package com.example.formation.imc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CheckBox ;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class MainActivity extends AppCompatActivity{

    // Déclaration des variables pour les évenements:
    private EditText editPoids = null;
    private EditText editTaille = null;
    private RadioButton radioButtonM = null;
    private RadioButton radioButtonC = null;
    private CheckBox checkBoxMF = null;
    private Button buttonIMC = null;
    private Button buttonRAZ = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage : :
        setContentView(R.layout.activity_main);

        // Recuperation des ressources :
        String poids = getResources().getString(R.string.poids);
        String taille = getResources().getString(R.string.taille);
        String metre = getResources().getString(R.string.metre);
        String centimetre = getResources().getString(R.string.centimetre);
        String mf = getResources().getString(R.string.mf);
        String imc = getResources().getString(R.string.imc);
        String raz = getResources().getString(R.string.raz);
        String res = getResources().getString(R.string.res);

        // Recuperation des zones de texte et des différents widgets :
        TextView poidsTxt = (TextView)findViewById(R.id.poidsTxt);
        EditText editPoids = (EditText)findViewById(R.id.editPoids);
        TextView tailleTxt = (TextView)findViewById(R.id.tailleTxt);
        EditText editTaille = (EditText)findViewById(R.id.editTaille);
        RadioButton radioButtonM = (RadioButton)findViewById(R.id.radioButtonM);
        RadioButton radioButtonC = (RadioButton)findViewById(R.id.radioButtonC);
        CheckBox checkBoxMF = (CheckBox)findViewById(R.id.checkBoxMF);
        Button buttonIMC = (Button)findViewById(R.id.buttonIMC);
        Button buttonRAZ = (Button)findViewById(R.id.buttonRAZ);
        TextView textViewRes = (TextView)findViewById(R.id.textViewRes);

        // Remplissage des zones de texte :
        poidsTxt.setText(poids);
        editPoids.setText(poids);
        tailleTxt.setText(taille);
        editTaille.setText(taille);
        radioButtonM.setText(metre);
        radioButtonC.setText(centimetre);
        checkBoxMF.setText(mf);
        buttonIMC.setText(imc);
        buttonRAZ.setText(raz);
        textViewRes.setText(res);

    }
}
