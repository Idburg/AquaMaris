package com.proyecto.aquamaris;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class Splash extends AppCompatActivity {

    private ImageView gifImageView;  // Agregar ImageView para el GIF
    private AppUpdateManager appUpdateManager;
    private static final int APP_UPDATE_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appUpdateManager = AppUpdateManagerFactory.create(this);
        checkForAppUpdate();


        gifImageView = findViewById(R.id.splash);  // Inicializar el ImageView para el GIF

        // Cargar imágenes con Gli

        // Cargar GIF con Glide
        Glide.with(this)
                .load(R.drawable.splash)  // Asegúrate de tener el GIF en res/drawable
                .into(gifImageView);



        // Abrir la aplicación después de la animación
        openApp();
    }


    private void openApp() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash.this, Login.class);
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

    /** @noinspection deprecation*/
    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE, // Flujo de actualización inmediata (pantalla completa)
                    this,
                    APP_UPDATE_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            Log.d("IntentSend","Error: "+e);
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
