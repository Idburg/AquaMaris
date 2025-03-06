package com.proyecto.aquamaris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private String email;
    private String password;
    private final View currentView = findViewById(android.R.id.content);

    FirebaseAuth mAuth;
    /** @noinspection deprecation*/
    GoogleSignInClient googleSignInClient;


    /** @noinspection deprecation*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button gLoginButton = findViewById(R.id.google_login);
        gLoginButton.setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        AppCompatButton logButton = findViewById(R.id.login_button);
        TextInputEditText passInput = findViewById(R.id.pass2);
        EditText emailInput = findViewById(R.id.mail);




        logButton.setOnClickListener(view -> {
            email = emailInput.getText().toString().trim();
            password = passInput.getText().toString().trim();

            if (!email.contains("gmail")) {
                if (email.isEmpty() || password.isEmpty())
                    Snackbar.make(currentView,"Por favor rellene los campos vacíos", 1500).show();
                else {
                    if (isValidEmail(email)) {
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().setPersistenceEnabled(true);

                                editor.putBoolean("rememberMe", true);
                                editor.putString("userEmail", email);
                                editor.apply();

                                Snackbar.make(currentView, "Iniciando sesión...", 1500).show();
                                mainPage();
                            } else
                                Snackbar.make(currentView,"Contraseña incorrecta",1500).show();
                        });
                    } else
                        Snackbar.make(currentView,"Formato de email no válido, intenta de nuevo",1500).show();
                }
            }
            else
                Snackbar.make(currentView,"Pulsa el otro botón para registrarse con Google",1500).show();
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

    /** @noinspection deprecation*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Snackbar.make(currentView,"Error en el inicio de sesión",1500).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Snackbar.make(currentView,"Bienvenido",1500).show();
                        mainPage();
                    } else {
                        Snackbar.make(currentView,"Error de autenticación",1500).show();
                    }
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
        Intent intent = new Intent(Login.this, Noticias.class);
        startActivity(intent);
        finish();
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}