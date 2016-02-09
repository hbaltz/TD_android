package com.example.formation.imc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CheckBox ;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    // Déclaration des variables pour les évenements:
    private EditText editPoids = null;
    private EditText editTaille = null;
    private RadioButton radioButtonM = null;
    private RadioButton radioButtonC = null;
    private CheckBox checkBoxMF = null;
    private Button buttonIMC = null;
    private Button buttonRAZ = null;
    private RadioGroup group = null;

    private TextView poidsTxt = null;
    private TextView tailleTxt = null;
    private TextView textViewRes = null;

    private String res = "Test";
    private String poids = null;
    private String taille = null;
    private String metre = null;
    private String centimetre = null;
    private String mf = null;
    private String imc = null;
    private String raz = null;
    private String trPti = null;
    private String mega = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage : :
        setContentView(R.layout.activity_main);

        // Recuperation des ressources :
        poids = getResources().getString(R.string.poids);
        taille = getResources().getString(R.string.taille);
        metre = getResources().getString(R.string.metre);
        centimetre = getResources().getString(R.string.centimetre);
        mf = getResources().getString(R.string.mf);
        imc = getResources().getString(R.string.imc);
        raz = getResources().getString(R.string.raz);
        res = getResources().getString(R.string.res);

        // Recuperation des views :
        poidsTxt = (TextView)findViewById(R.id.poidsTxt);
        editPoids = (EditText)findViewById(R.id.editPoids);
        tailleTxt = (TextView)findViewById(R.id.tailleTxt);
        editTaille = (EditText)findViewById(R.id.editTaille);
        radioButtonM = (RadioButton)findViewById(R.id.radioButtonM);
        radioButtonC = (RadioButton)findViewById(R.id.radioButtonC);
        checkBoxMF = (CheckBox)findViewById(R.id.checkBoxMF);
        buttonIMC = (Button)findViewById(R.id.buttonIMC);
        buttonRAZ = (Button)findViewById(R.id.buttonRAZ);
        textViewRes = (TextView)findViewById(R.id.textViewRes);
        group = (RadioGroup)findViewById(R.id.group);
        mega = getResources().getString(R.string.mega);

        // Remplissage des zones de texte :
        poidsTxt.setText(poids);
        editPoids.setHint(poids);
        tailleTxt.setText(taille);
        editTaille.setHint(taille);
        radioButtonM.setText(metre);
        radioButtonC.setText(centimetre);
        checkBoxMF.setText(mf);
        buttonIMC.setText(imc);
        buttonRAZ.setText(raz);
        textViewRes.setText(res);

        // Attribution des listeners :
        buttonIMC.setOnClickListener(buttonIMCListener);
        buttonRAZ.setOnClickListener(buttonRAZListener);
        tailleTxt.addTextChangedListener(textWatcher);
        poidsTxt.addTextChangedListener(textWatcher);
        checkBoxMF.setOnClickListener(checkedListener);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textViewRes.setText(res);
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };

    // Uniquement pour le bouton imc :
    private OnClickListener buttonIMCListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!checkBoxMF.isChecked()) {
                // On récupère la taille
                String t = editPoids.getText().toString();
                // On récupère le poids
                String p = editTaille.getText().toString();

                float tValue = Float.valueOf(t);

                trPti = getResources().getString(R.string.trPti);

                // On vérifie que la taille est cohérente :
                if(tValue == 0) {
                    Toast.makeText(MainActivity.this, trPti, Toast.LENGTH_SHORT).show();
                }else {
                    float pValue = Float.valueOf(p);
                    // Si l'utilisateur a indiqué que la taille était en centimètres :
                    if(group.getCheckedRadioButtonId() == R.id.radioButtonC) {
                        tValue = tValue / 100;
                    }

                    tValue = (float)Math.pow(tValue, 2);
                    float imc = pValue / tValue;
                    textViewRes.setText("Votre IMC est " + String.valueOf(imc));
                }
            }else{
                textViewRes.setText(mega);
            }
        }
    };

    // Listener du bouton de remise à zéro
    private OnClickListener buttonRAZListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            editPoids.getText().clear();
            editTaille.getText().clear();
            textViewRes.setText(res);
        }
    };

    // Listener du bouton de la megafonction.
    private OnClickListener checkedListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // On remet le texte par défaut si c'était le texte de la megafonction qui était écrit
            if(!((CheckBox)v).isChecked() && textViewRes.getText().equals(mega))
                textViewRes.setText(res);
        }
    };
}
