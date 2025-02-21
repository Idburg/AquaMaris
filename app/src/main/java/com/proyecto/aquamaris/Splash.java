package com.proyecto.aquamaris;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.animation.ObjectAnimator;
import android.view.View; // Asegúrate de tener esta importación
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    private ImageView pez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //FirebaseAuth.getInstance().signOut();

        ImageView fondo = findViewById(R.id.fondo);
        ImageView logo = findViewById(R.id.logo);
        ImageView pezuno = findViewById(R.id.pezuno);
        pez = findViewById(R.id.pez);

        // Cargar imágenes con Glide
        Glide.with(this)
                .load(R.drawable.pez)
                .into(fondo);

        Glide.with(this)
                .load(R.drawable.logo)
                .dontTransform() // Evita transformaciones que podrían alterar la imagen
                .into(logo);


        // Animar el pez
        animateFish();

        // Manejar insets para padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Abrir la aplicación después de la animación
        openApp();
    }

    private void animateFish() {
        pez.setVisibility(View.VISIBLE);

        // Animación de movimiento horizontal (de izquierda a derecha)
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(pez, "translationX", -600f, 20f); // Ajusta el valor final según tu necesidad
        animatorX.setDuration(3000);
        animatorX.setInterpolator(new LinearInterpolator());

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(pez, "translationY", 220f, 220f);
        animatorY.setDuration(3000);
        animatorY.setInterpolator(new LinearInterpolator());

        // No se usa animación vertical, ya que el pez no debe moverse hacia abajo
        // Si es necesario que el pez se posicione encima de la "M", ajusta la posición en "translationY" previamente

        // Dibujar sombra mientras el pez se mueve


        // Iniciar la animación
        animatorY.start();
        animatorX.start();
    }



    private void openApp() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash.this, Noticias.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }, 4000); // Retraso de 4 segundos
    }
}
