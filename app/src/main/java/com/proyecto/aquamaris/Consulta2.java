package com.proyecto.aquamaris;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.aquamaris.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Consulta2 extends AppCompatActivity {
    String prov;
    List<ListarElementos> elements2;
    TextView resultado2;
    Button inf;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)throws IllegalStateException, NullPointerException {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.consulta2);

        try{
            DBHelper db2 = new DBHelper(this);
            SQLiteDatabase obj = db2.getReadableDatabase();
            prov = getIntent().getExtras().getString("PROV");
            Cursor c2 = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+prov+"%'", null);
            if(c2 != null && c2.moveToFirst())
            {
                elements2 = new ArrayList<>();
                do{

                    int indiceN = c2.getColumnIndex("nombre_cientifico");
                    int indicePV = c2.getColumnIndex("provincias");

                    String nombrecientifico = c2.getString(indiceN);
                    String provinciass = c2.getString(indicePV);

                    //contador++;

                    Log.d("Consulta", "Provincia encontrada: " + provinciass);
                    elements2.add(new ListarElementos("#775447", nombrecientifico, provinciass, "Ver"));
                }while(c2.moveToNext());
                c2.close();
            }
            init2();




        }catch(Exception e)
        {
            System.out.println(e.toString());
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }



    }
    public void init2()
    {
        ListAdapter listAdapter2 = new ListAdapter(elements2, this);
        RecyclerView recyclerView2 = findViewById(R.id.listRecycleView);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(listAdapter2);

    }
}
