package com.proyecto.aquamaris;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
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

public class Register extends AppCompatActivity {
    FirebaseAuth mAuth;
    private String tel;
    private String passTxt;
    private String emailTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        EditText emailInput = findViewById(R.id.mail2);
        EditText phoneInput = findViewById(R.id.phone);
        TextInputEditText passInput = findViewById(R.id.pass3);
        AppCompatButton signup_button = findViewById(R.id.signup_button);

        signup_button.setOnClickListener(view -> {
            emailTxt = emailInput.getText().toString().trim();
            passTxt = passInput.getText().toString().trim();
            tel = phoneInput.getText().toString();

            if (emailTxt.isEmpty() || passTxt.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Los campos vacíos deben rellenarse",Toast.LENGTH_SHORT).show();

            }
            else {
                if (isValidEmail(emailTxt)) {
                    if (passTxt.length() > 6) {
                        mAuth.createUserWithEmailAndPassword(emailTxt,passTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
                                    backToLogin();
                                }
                                else
                                    Toast.makeText(getApplicationContext(),"Hubo un fallo con la creación de cuentas",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                        Toast.makeText(getApplicationContext(),"La contraseña debe tener por lo menos 7 caracteres",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Formato de email inválido",Toast.LENGTH_SHORT).show();
            }

        });

        TextView login_link = findViewById(R.id.login_link);
        login_link.setOnClickListener(view -> {
            backToLogin();
        });

        EditText datePopUp = findViewById(R.id.popup_birthday);
        datePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                datePopUp.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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