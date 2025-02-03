package com.example.aquamaris;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    String province;

    public Consulta () {}

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) throws IllegalStateException, NullPointerException{
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consulta);
        resultado = findViewById(R.id.textView2);

        try {
            DBHelper db = new DBHelper(this);
            SQLiteDatabase obj = db.getReadableDatabase();
            province = getIntent().getExtras().getString("PROVINCIA");
            Cursor c = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+province+"%'", null);
            Log.d("ValorProvincia", "Provincia: " + province);
            //Cursor c = obj.rawQuery("SELECT * FROM peces WHERE LOWER(provincias) LIKE LOWER(?)", new String[]{"%" + provincia.toLowerCase() + "%"});
            if(c != null && c.moveToFirst())
            {
                elements = new ArrayList<>();
                do{

                    int indiceN = c.getColumnIndex("nombre_cientifico");
                    int indicePV = c.getColumnIndex("provincias");

                    String nombrecientifico = c.getString(indiceN);
                    String provinciass = c.getString(indicePV);

                    //contador++;

                    Log.d("Consulta", "Provincia encontrada: " + provinciass);
                    elements.add(new ListarElementos("#775447", nombrecientifico, provinciass, "Ver"));
                }while(c.moveToNext());
                c.close();
            }

            init();
        }catch(Exception e)
        {
           Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void init()
    {

        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }



}