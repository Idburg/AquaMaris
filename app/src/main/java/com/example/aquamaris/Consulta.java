package com.example.aquamaris;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquamaris.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Consulta extends AppCompatActivity {

    TextView resultado;
    List<ListarElementos> elements;
    int contador = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) throws IllegalStateException{
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consulta);
        resultado = findViewById(R.id.textView2);
        String provincia = getIntent().getStringExtra("PROVINCIA");


        try {
            DBHelper db = new DBHelper(this);
            SQLiteDatabase obj = db.getReadableDatabase();
            Cursor c = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+provincia+"%'",null);
            if(c != null)
            {
                c.moveToFirst();
                do{
                    //int indiceP = c.getColumnIndex("pais");
                    int indiceN = c.getColumnIndex("nombre_científico");
                    //int indiceF = c.getColumnIndex("familia");
                    //int indiceL = c.getColumnIndex("localizacion");
                    int indicePV = c.getColumnIndex("provincias");

                    //String pais = c.getString(indiceP);
                    String nombrecientifico = c.getString(indiceN);
                    //String familia = c.getString(indiceF);
                    //String localizacion = c.getString(indiceL);
                    String provincias = c.getString(indicePV);
                    contador++;

                    resultado.setText(resultado.getText()+" "+nombrecientifico+" "+provincias+"\n");

                    //" "+pais+
                    //" "+familia+" "+localizacion+
                    //
                }while(c.moveToNext());
            }

            db.close();

        }catch(Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }


        init();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void init()
    {
        elements = new ArrayList<>();
        for(int i = 0; i < contador; i++)
        {
            elements.add(new ListarElementos("#775447","Pedro","Mexico","Activo"));
        }
        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}