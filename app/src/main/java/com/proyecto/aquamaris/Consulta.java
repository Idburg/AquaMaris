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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
    protected void onCreate(Bundle savedInstanceState) throws IllegalStateException, NullPointerException {
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

            switch (province) {
                case "alava":
                    province = province.replace("alava", "Álava");
                    break;
                case "almeria":
                    province = province.replace("almeria", "Almería");
                    break;
                case "avila":
                    province = province.replace("avila", "Ávila");
                    break;
                case "caceres":
                    province = province.replace("caceres", "Cáceres");
                    break;
                case "cadiz":
                    province = province.replace("cadiz", "Cádiz");
                    break;
                case "castellon":
                    province = province.replace("castellon", "Castellón");
                    break;
                case "cordoba":
                    province = province.replace("cordoba", "Córdoba");
                    break;
                case "gipuzcoa":
                    province = province.replace("gipuzcoa", "Gipúzcoa");
                    break;
                case "jaen":
                    province = province.replace("jaen", "Jaén");
                    break;
                case "leon":
                    province = province.replace("leon", "León");
                    break;
                case "lerida":
                    province = province.replace("lerida", "Lérida");
                    break;
                case "malaga":
                    province = province.replace("malaga", "Málaga");
                    break;
                default:
                    char firstLetter = province.charAt(0);
                    province = province.replace(province.charAt(0), Character.toUpperCase(firstLetter));
                    break;
            }

            /*
            if(province.contains("alava") || province.contains("almeria") || province.contains("avila")|| province.contains("caceres")
                    || province.contains("cadiz") || province.contains("castellon") || province.contains("cordoba") || province.contains("gipuzcoa")
                    || province.contains("jaen") || province.contains("leon") || province.contains("lerida") || province.contains("malaga")) {
                province = province.replace("alava", "Álava");
                province = province.replace("almeria", "Almería");
                province = province.replace("avila", "Ávila");
                province = province.replace("caceres", "Cáceres");
                province = province.replace("cadiz", "Cádiz");
                province = province.replace("castellon", "Castellón");
                province = province.replace("cordoba", "Córdoba");
                province = province.replace("gipuzcoa", "Gipúzcoa");
                province = province.replace("jaen", "Jaén");
                province = province.replace("leon", "León");
                province = province.replace("lerida", "Lérida");
                province = province.replace("malaga", "Málaga");
            }
            */

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
                    String urlImagen = getUrlImagen(nombrecientifico);

                    Log.d("Consulta", "Provincia encontrada: " + provinciass);
                    elements.add(new ListarElementos(urlImagen, nombrecientifico, provinciass, "Ver"));
                } while (c.moveToNext());
                c.close();
            }

            init();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void init() {
        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    public String getUrlImagen(String indiceN) {
        final String[] imagenUrl = {""}; // Usamos un array para modificarlo dentro del hilo

        // Hacer la solicitud en un hilo para evitar bloquear el hilo principal
        new Thread(() -> {
            try {
                // Conectar a la página de Wikipedia del pez
                String urlWiki = "https://es.wikipedia.org/wiki/" + indiceN.replace(" ", "_");
                Document doc = Jsoup.connect(urlWiki).get();

                Elements images = doc.select("mw-file-element");
                String imgUrl = "";
                System.out.println("Imagenes: "+images.size());

                // Buscamos la imagen
                for (Element image : images) {
                    String imageSrc = "https:" + image.attr("src");
                    imgUrl = imageSrc; // Asignamos el URL de la imagen encontrada
                    System.out.println(imageSrc);
                }

                // Si se encontró una imagen válida, la cargamos
                if (!imgUrl.isEmpty()) {
                    imagenUrl[0] = imgUrl;
                } else {
                    System.out.println("Imagen: noImage");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Retornar la URL de la imagen (en este caso, estará vacía al principio)
        return imagenUrl[0];
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
