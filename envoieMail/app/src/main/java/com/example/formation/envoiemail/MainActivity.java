package com.example.formation.envoiemail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//TODO dans string renseigner les destinatires

public class MainActivity extends AppCompatActivity {

    // Déclaration des variables
    private EditText editText = null;
    private Button button = null;

    private String bt = null;
    private String mail = null;
    private String msg = null;
    private String to = null;
    private String cc = null;
    private String sjt = null;

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

        // Attribution des listeners :
        button.setOnClickListener(buttonListener);

    }

    /**
     * Gestion des évenements :
     */

    // Listener du bouton d'envoie
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("Send email", "");

            // Recuperation des ressources :
            to = getResources().getString(R.string.to);
            cc = getResources().getString(R.string.cc);
            sjt = getResources().getString(R.string.sjt);

            // Récupération du message
            msg = editText.getText().toString();

            String[] TO = {to};
            String[] CC = {cc};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");


            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, sjt);
            emailIntent.putExtra(Intent.EXTRA_TEXT, msg);

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
                Log.i("Finished sending email...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }

        }
    };
}
