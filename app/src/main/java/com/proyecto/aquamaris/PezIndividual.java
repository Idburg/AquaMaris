package com.proyecto.aquamaris;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PezIndividual extends AppCompatActivity {

    private WebView miVisorWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pez_individual);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);  // Establece el Toolbar como ActionBar

        // Configura la ActionBar y habilita la flecha "volver"
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Habilita la flecha de "volver"
            getSupportActionBar().setDisplayShowHomeEnabled(true);  // Asegúrate de que el ícono de la home se vea
        }


        String nombrepez = getIntent().getStringExtra("PEZ");

        miVisorWeb = (WebView) findViewById(R.id.vistaWeb);
        WebSettings websettings = miVisorWeb.getSettings();
        websettings.setLoadWithOverviewMode(true);
        websettings.setUseWideViewPort(true);
        miVisorWeb.loadUrl("https://wikipedia.com/wiki/"+nombrepez);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Finaliza la actividad actual para que no quede en el stack
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}