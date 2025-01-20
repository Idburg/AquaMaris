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
    String provincia, prov;

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
            provincia = getIntent().getExtras().getString("PROVINCIA");

            Cursor c = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+provincia+"%'", null);
            Log.d("ValorProvincia", "Provincia: " + provincia);
            //Cursor c = obj.rawQuery("SELECT * FROM peces WHERE LOWER(provincias) LIKE LOWER(?)", new String[]{"%" + provincia.toLowerCase() + "%"});
            if(c != null && c.moveToFirst())
            {
                elements = new ArrayList<>();
                do{

                    int indiceN = c.getColumnIndex("nombre_científico");
                    int indicePV = c.getColumnIndex("provincias");

                    String nombrecientifico = c.getString(indiceN);
                    String provinciass = c.getString(indicePV);

                    contador++;

                    Log.d("Consulta", "Provincia encontrada: " + provinciass);
                    elements.add(new ListarElementos("#775447", nombrecientifico, provinciass, "Ver"));
                }while(c.moveToNext());
            }
            else
            {
                Toast.makeText(this, "No se encontraron resultados para esta provincia.", Toast.LENGTH_SHORT).show();
                Log.d("Consulta", "No se encontraron resultados.");
                try{

                    DBHelper db2 = new DBHelper(this);
                    SQLiteDatabase obj2 = db2.getReadableDatabase();
                    prov = getIntent().getStringExtra("PROV");
                    Cursor c2 = obj2.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+prov+"%'", null);
                    if(c2 != null && c2.moveToFirst())
                    {
                        elements = new ArrayList<>();
                        do{

                            int indiceN2 = c2.getColumnIndex("nombre_científico");
                            int indicePV2 = c2.getColumnIndex("provincias");

                            String nombrecientifico2 = c2.getString(indiceN2);
                            String provinciass2 = c2.getString(indicePV2);

                            contador++;

                            Log.d("Consulta", "Provincia encontrada: " + provinciass2);
                            elements.add(new ListarElementos("#775447", nombrecientifico2, provinciass2, "Ver"));
                        }while(c2.moveToNext());
                    }
                    else
                    {
                        Toast.makeText(this, "No se encontraron resultados para esta provincia. Mediante los botones", Toast.LENGTH_SHORT).show();
                        //Log.d("Consulta", "No se encontraron resultados. Mediante los botones");
                    }
                    c2.close();
                    db2.close();

                }catch(Exception e)
                {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                }

                init2();

                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });
            }
                c.close();
                db.close();

            init();
            }catch(Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }

        try{

            DBHelper db2 = new DBHelper(this);
            SQLiteDatabase obj2 = db2.getReadableDatabase();
            prov = getIntent().getStringExtra("PROV");
            Cursor c2 = obj2.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+prov+"%'", null);
            if(c2 != null && c2.moveToFirst())
            {
                elements = new ArrayList<>();
                do{

                    int indiceN2 = c2.getColumnIndex("nombre_científico");
                    int indicePV2 = c2.getColumnIndex("provincias");

                    String nombrecientifico2 = c2.getString(indiceN2);
                    String provinciass2 = c2.getString(indicePV2);

                    contador++;

                    Log.d("Consulta", "Provincia encontrada: " + provinciass2);
                    elements.add(new ListarElementos("#775447", nombrecientifico2, provinciass2, "Ver"));
                }while(c2.moveToNext());
            }
            else
            {
                Toast.makeText(this, "No se encontraron resultados para esta provincia. Mediante los botones", Toast.LENGTH_SHORT).show();
                //Log.d("Consulta", "No se encontraron resultados. Mediante los botones");
            }
            c2.close();
            db2.close();

            }catch(Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }

            init2();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void init()
    {

        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    public void init2()
    {
        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}