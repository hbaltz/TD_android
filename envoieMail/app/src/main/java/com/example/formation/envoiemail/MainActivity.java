package com.example.formation.envoiemail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Déclaration des variables
    private EditText editText = null;
    private Button button = null;

    private String bt = null;
    private String mail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperation des ressources :
        bt = getResources().getString(R.string.bt);
        mail = getResources().getString(R.string.mail);

        // Recuperation des views :
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);

        // Remplissage des zones de texte :
        editText.setHint(mail);
        button.setText(bt);

    }
}
