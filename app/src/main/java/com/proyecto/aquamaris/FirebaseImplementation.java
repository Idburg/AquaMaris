package com.proyecto.aquamaris;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirebaseImplementation extends AppCompatActivity {


    // Instanciamos FirebaseFirestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<ListarElementos> elements;
    Consulta consulta =  new Consulta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebaseimplementation);

        // Referencia al botón en la UI
        Button btnAgregarUsuarios = findViewById(R.id.btnAgregarUsuarios);

        // Configurar el evento onClick para el botón
        btnAgregarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Crear la lista de usuarios
                elements = new ArrayList<>();
                elements.add(new ListarElementos("#775447", "a", "2", "Ver"));
                // Obtener la referencia a la colección "usuarios"
                CollectionReference usuariosRef = db.collection("peces");

                // Subir los usuarios a Firestore
                for (int i = 0; i < elements.size(); i++) {
                    // Agregar el usuario a Firestore
                    usuariosRef.add(elements.get(i))
                            .addOnSuccessListener(documentReference -> {
                                // Usuario agregado con éxito
                                System.out.println("Usuario agregado con ID: " + documentReference.getId());
                            })
                            .addOnFailureListener(e -> {
                                // Error al agregar el usuario
                                System.out.println("Error al agregar el usuario: " + e.getMessage());
                            });
                }
            }
        });
    }

}
