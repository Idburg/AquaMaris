package com.proyecto.aquamaris;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        AppCompatButton logButton = findViewById(R.id.login_button);
        logButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Noticias.class);
            startActivity(intent);
        });

        TextView signup_link = findViewById(R.id.signup_link);
        signup_link.setOnClickListener(view -> {
            signup_link.setPaintFlags(signup_link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}