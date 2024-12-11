package com.example.aquamaris;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView fondo = findViewById(R.id.backView);
        ImageView logo = findViewById(R.id.logo);

        // Cargar imágenes con Glide
        Glide.with(this)
                .load(R.drawable.pez)
                .centerCrop()
                .into(fondo);
        Glide.with(this)
                .load(R.drawable.logo)
                .centerCrop()
                .into(logo);

        // Aplicar animación al logo
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo.startAnimation(animation);

        openApp();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openApp() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }, 4000); // Retraso de 4 segundos
    }
}
