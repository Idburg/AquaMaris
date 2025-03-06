package com.proyecto.aquamaris;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
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

/**
 * @noinspection deprecation
 */
public class Register extends AppCompatActivity {
    GoogleSignInClient googleSignInClient;
    FirebaseAuth mAuth;
    private String passTxt;
    private String emailTxt;
    private final View currentView = findViewById(android.R.id.content);
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        EditText emailInput = findViewById(R.id.mail2);
        TextInputEditText passInput = findViewById(R.id.pass3);
        AppCompatButton signup_button = findViewById(R.id.signup_button);
        Button googleRegisterButton = findViewById(R.id.google_register);

        googleRegisterButton.setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        signup_button.setOnClickListener(view -> {
            emailTxt = emailInput.getText().toString().trim();
            passTxt = passInput.getText().toString().trim();

            if (emailTxt.isEmpty() || passTxt.isEmpty())
                Snackbar.make(currentView, "Los campos vacíos deben rellenarse", 1500).show();
            else {
                if (isValidEmail(emailTxt)) {
                    if (passTxt.length() > 6) {
                        mAuth.createUserWithEmailAndPassword(emailTxt, passTxt).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Snackbar.make(currentView, "Cuenta creada correctamente", 1500).show();
                                backToLogin();
                            } else
                                Snackbar.make(currentView, "Hubo un fallo con la creación de cuentas", 1500).show();
                        });
                    } else
                        Snackbar.make(currentView, "La contraseña debe tener por lo menos 7 caracteres", 1500).show();
                } else
                    Snackbar.make(currentView, "Formato de email inválido", 1500).show();
            }

        });

        TextView login_link = findViewById(R.id.login_link);
        login_link.setOnClickListener(view -> backToLogin());

        EditText datePopUp = findViewById(R.id.popup_birthday);
        datePopUp.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Register.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // on below line we are setting date to our edit text.
                        datePopUp.setText(dayOfMonth + getString(R.string.slash) + (monthOfYear + 1) + getString(R.string.slash) + year1);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

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
                Snackbar.make(currentView, "Error en el inicio de sesión", 1500).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful())
                        Snackbar.make(currentView, "Bienvenido", 1500).show();
                    else
                        Snackbar.make(currentView, "Error de autenticación", 1500).show();
                });
    }

    private void backToLogin() {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}