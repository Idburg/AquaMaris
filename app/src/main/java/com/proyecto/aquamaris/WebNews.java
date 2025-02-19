package com.proyecto.aquamaris;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WebNews extends AppCompatActivity {
    private WebView webView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_news);

        // Configura el Toolbar como ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Establece el Toolbar como ActionBar

        // Configura la ActionBar y habilita la flecha "volver"
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Habilita la flecha de "volver"
            getSupportActionBar().setDisplayShowHomeEnabled(true);  // Asegúrate de que el ícono de la home se vea
        }

        // Configura el WebView
        webView = findViewById(R.id.WebView);
        String url = getIntent().getStringExtra("url");

        if (url != null) {
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }
    }

    // Maneja el clic en la flecha de "volver"
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Al presionar la flecha, vuelve a la actividad Noticias
            Intent intent = new Intent(WebNews.this, Noticias.class);
            startActivity(intent);  // Inicia la actividad de Noticias
            finish();  // Finaliza la actividad actual para que no quede en el stack
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
