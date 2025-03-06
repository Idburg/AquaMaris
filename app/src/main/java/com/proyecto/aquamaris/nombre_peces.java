package com.proyecto.aquamaris;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.aquamaris.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class nombre_peces extends AppCompatActivity {

    String province;
    TextView resultado2;
    List<NombrePeces> pecesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nombre_peces);

        try {
            DBHelper db = new DBHelper(this);
            SQLiteDatabase obj = db.getReadableDatabase();
            province = getIntent().getExtras().getString("PROVINCIA");
            Cursor c = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+province+"%'", null);
            Log.d("ValorProvincia", "Provincia: " + province);
            //Cursor c = obj.rawQuery("SELECT * FROM peces WHERE LOWER(provincias) LIKE LOWER(?)", new String[]{"%" + provincia.toLowerCase() + "%"});
            if(c != null && c.moveToFirst())
            {
                pecesList = new ArrayList<>();
                do{

                    int indiceN = c.getColumnIndex("nombre_cientifico");

                    String nombrecientifico = c.getString(indiceN);

                    //contador++;

                    Log.d("Consulta", "Provincia encontrada: ");
                    pecesList.add(new NombrePeces(nombrecientifico));
                }while(c.moveToNext());
                c.close();
            }

            init3();
        }catch(Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public void init3()
    {

        NombrePecesAdapter npa = new NombrePecesAdapter(pecesList, this);
        RecyclerView recyclerView = findViewById(R.id.listRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(npa);
    }
}