package com.proyecto.aquamaris;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private String email;
    private String password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        AppCompatButton logButton = findViewById(R.id.login_button);
        TextInputEditText passInput = findViewById(R.id.pass2);
        EditText emailInput = findViewById(R.id.mail);

        mAuth = FirebaseAuth.getInstance();

        logButton.setOnClickListener(view -> {
            email = emailInput.getText().toString().trim();
            password = passInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty())
                Toast.makeText(getApplicationContext(),"Por favor rellene los campos vacíos",Toast.LENGTH_SHORT).show();
            else {
                if (isValidEmail(email)) {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                                Toast.makeText(getApplicationContext(), "Iniciando sesión...", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(), "Formato de email no válido, intenta de nuevo", Toast.LENGTH_SHORT).show();
            }


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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuario = mAuth.getCurrentUser();
        if (usuario != null)
            mainPage();
    }

    private void mainPage() {
        Intent intent = new Intent(this, Noticias.class);
        startActivity(intent);
        finish();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}