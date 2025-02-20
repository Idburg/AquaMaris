package com.proyecto.aquamaris;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
}