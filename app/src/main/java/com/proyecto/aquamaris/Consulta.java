package com.proyecto.aquamaris;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.aquamaris.Fragmentos.Prueba2;
import com.proyecto.aquamaris.db.DBHelper;

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

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);  // Establece el Toolbar como ActionBar

// Configura la ActionBar y habilita la flecha "volver"
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Habilita la flecha de "volver"
            getSupportActionBar().setDisplayShowHomeEnabled(true);  // Asegúrate de que el ícono de la home se vea
        }

        try {
            DBHelper db = new DBHelper(this);
            SQLiteDatabase obj = db.getReadableDatabase();
            province = getIntent().getExtras().getString("PROVINCIA").toLowerCase();
            assert province != null;
            if(province.contains("alava") || province.contains("almeria") || province.contains("avila")|| province.contains("caceres")
            || province.contains("cadiz") || province.contains("castellon") || province.contains("cordoba") || province.contains("gipuzcoa")
            || province.contains("jaen") || province.contains("leon") || province.contains("lerida") || province.contains("malaga"))
            {
                province = province.replace("alava","Álava");
                province = province.replace("almeria","Almería");
                province = province.replace("avila","Ávila");
                province = province.replace("caceres","Cáceres");
                province = province.replace("cadiz","Cádiz");
                province = province.replace("castellon","Castellón");
                province = province.replace("cordoba","Córdoba");
                province = province.replace("gipuzcoa","Gipúzcoa");
                province = province.replace("jaen","Jaén");
                province = province.replace("leon","León");
                province = province.replace("lerida","Lérida");
                province = province.replace("malaga","Málaga");


                Cursor c = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%"+province.trim()+"%'", null);
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
            }

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

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Finaliza la actividad actual para que no quede en el stack
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}