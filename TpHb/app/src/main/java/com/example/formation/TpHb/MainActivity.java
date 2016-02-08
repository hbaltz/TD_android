package com.example.formation.TpHb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CheckBox ;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage : :
        setContentView(R.layout.activity_main);

        // Utilisation d'une barre d'action :
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*

        // Utilisation d'un bouton :
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action ! La flemme !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Modification d'une chaine de caractere recuperee dans une ressource :
        String hello = getResources().getString(R.string.hello_world);
        hello = hello.replace("World", "Hugo");

        // Utilisation des ressources pour modifier du texte :
        TextView text = (TextView)findViewById(R.id.my_text_view);
        text.setText(hello);

        */

        /**
        Initiliastion de l'interface pour le calcul de l'IMC :
         **/

        // Recuperation des ressources :
        String poids = getResources().getString(R.string.poids);
        String taille = getResources().getString(R.string.taille);
        String metre = getResources().getString(R.string.metre);
        String centimetre = getResources().getString(R.string.centimetre);
        String mf = getResources().getString(R.string.mf);
        String imc = getResources().getString(R.string.imc);
        String raz = getResources().getString(R.string.raz);
        String res = getResources().getString(R.string.res);

        // Recuperation des zones de texte
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
