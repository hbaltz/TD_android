/**
 * Application qui agrandi un texte au toucher
 * Basée sur un exemple d'openclassroom
 */

package com.example.formation.test_bouton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Recuperation des ressources :
        String hello = getResources().getString(R.string.hello);

        // Recuperation du bouton :
        Button bouton = (Button)findViewById(R.id.bouton);

        // Remplissage des zones de texte :
        bouton.setText(hello);

        // On indique que cette classe sera son listener pour l'évènement Touch
        bouton.setOnTouchListener((View.OnTouchListener) this);
    }

    // Fonction lancée au toucher
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // Comme l'évènement nous donne la vue concernée par le toucher, on le récupère et on le caste en Button
        Button bouton = (Button)view;

        // On récupère la largeur du bouton
        int largeur = bouton.getWidth();
        // On récupère la hauteur du bouton
        int hauteur = bouton.getHeight();

        // On récupère la coordonnée sur l'abscisse (X) de l'évènement
        float x = event.getX();
        // On récupère la coordonnée sur l'ordonnée (Y) de l'évènement
        float y = event.getY();

        // On change la taille du texte :
        bouton.setTextSize(Math.abs(x - largeur / 2) + Math.abs(y - hauteur / 2));
        // Le toucher est fini, on veut continuer à détecter les touchers d'après :
        return true;
    }
}
