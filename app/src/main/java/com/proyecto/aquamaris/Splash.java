package com.proyecto.aquamaris;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.firebase.auth.FirebaseAuth;
import android.util.DisplayMetrics;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

public class Splash extends AppCompatActivity {

    private ImageView pez;
    private AppUpdateManager appUpdateManager;
    private static final int APP_UPDATE_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //FirebaseAuth.getInstance().signOut();
        appUpdateManager = AppUpdateManagerFactory.create(this);
        checkForAppUpdate();

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

        // Obtener el tamaño de la pantalla
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenWidth = displayMetrics.widthPixels; // Ancho de la pantalla en píxeles
        float screenHeight = displayMetrics.heightPixels; // Alto de la pantalla en píxeles

        // Ajustar las posiciones en función del tamaño de la pantalla
        float finalPositionX = screenWidth * 0.04f; // 70% del ancho de la pantalla
        float initialPositionX = -screenWidth * 0.5f;

        // Posición Y ajustada a un valor fijo, puedes modificarla si es necesario
        float finalPositionY = screenHeight * 0.1f; // Ajustar a la mitad de la pantalla

        // Animación de movimiento horizontal (de izquierda a derecha)
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(pez, "translationX", initialPositionX, finalPositionX);
        animatorX.setDuration(3000); // Duración de la animación
        animatorX.setInterpolator(new LinearInterpolator());

        // Animación de movimiento vertical (opcional, puedes ajustarla según tus necesidades)
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(pez, "translationY", finalPositionY, finalPositionY);
        animatorY.setDuration(3000); // Duración de la animación
        animatorY.setInterpolator(new LinearInterpolator());

        // Iniciar las animaciones
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

    private void checkForAppUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) { // Forzar actualización inmediata
                startUpdateFlow(appUpdateInfo);
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE, // Flujo de actualización inmediata (pantalla completa)
                    this,
                    APP_UPDATE_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) { // Si el usuario cancela o falla
                finish(); // Cierra la app para forzar la actualización
            }
        }
    }

}
