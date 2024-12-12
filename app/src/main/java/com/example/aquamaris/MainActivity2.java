package com.example.aquamaris;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aquamaris.db.DBHelper;

public class MainActivity2 extends AppCompatActivity {

    private TextView txt;
    private EditText et;
    String contenido = "";
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);


        et = findViewById(R.id.editTextText);
        contenido = et.getText().toString();
        b = findViewById(R.id.button3);
        txt = findViewById(R.id.texto);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contenido.contains("Madrid"))
                {
                    DBHelper dbhelper = null;
                    try {
                        dbhelper = new DBHelper(MainActivity2.class.newInstance());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                    SQLiteDatabase db = dbhelper.getWritableDatabase();

                    Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                    startActivity(intent);

                    intent.putExtra("PECES",contenido);

                    String resultado = getIntent().getStringExtra("PECES");

                    txt.setText(resultado);
                }


            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}