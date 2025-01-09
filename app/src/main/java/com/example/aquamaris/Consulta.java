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
    String provincia;

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
            provincia.replaceAll(",","");
            provincia.replaceAll("á","a");
            provincia.replaceAll("é","e");
            provincia.replaceAll("í","i");
            provincia.replaceAll("ó","o");
            provincia.replaceAll("ú","u");
            provincia.replaceAll(",","");
            provincia.replaceAll(" ","");
            Cursor c = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+provincia+"%'", null);
            Log.d("ValorProvincia", "Provincia: " + provincia);
            //Cursor c = obj.rawQuery("SELECT * FROM peces WHERE LOWER(provincias) LIKE LOWER(?)", new String[]{"%" + provincia.toLowerCase() + "%"});
            if(c != null && c.moveToFirst())
            {
                elements = new ArrayList<>();
                do{
                    /*
                    //int indiceP = c.getColumnIndex("pais");
                    int indiceN = c.getColumnIndex("nombre_científico");
                    //int indiceF = c.getColumnIndex("familia");
                    //int indiceL = c.getColumnIndex("localizacion");
                    int indicePV = c.getColumnIndex("provincias");

                    //String pais = c.getString(indiceP);
                    String nombrecientifico = c.getString(indiceN);
                    //String familia = c.getString(indiceF);
                    //String localizacion = c.getString(indiceL);
                    String provinciass = c.getString(indicePV);

                    contador++;


                    for(int i = 0; i < contador; i++)
                    {
                        elements.add(new ListarElementos("#775447",nombrecientifico,provinciass,"Ver"));
                    }

                    //resultado.setText(resultado.getText()+" "+nombrecientifico+" "+provinciass+"\n");

                    //" "+pais+
                    //" "+familia+" "+localizacion+
                    //
                    */

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
            }
            c.close();
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

        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}