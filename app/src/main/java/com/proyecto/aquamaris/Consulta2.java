package com.proyecto.aquamaris;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.aquamaris.Fragmentos.Prueba2;
import com.proyecto.aquamaris.db.DBHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Consulta2 extends AppCompatActivity {
    String prov;
    List<ListarElementos> elements2;
    TextView resultado2;
    Button inf;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) throws IllegalStateException, NullPointerException {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.consulta2);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);  // Establece el Toolbar como ActionBar

        // Configura la ActionBar y habilita la flecha "volver"
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Habilita la flecha de "volver"
            getSupportActionBar().setDisplayShowHomeEnabled(true);  // Asegúrate de que el ícono de la home se vea
        }

        try {
            DBHelper db2 = new DBHelper(this);
            SQLiteDatabase obj = db2.getReadableDatabase();
            prov = getIntent().getExtras().getString("PROV");
            Cursor c2 = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%" + prov + "%'", null);
            if (c2 != null && c2.moveToFirst()) {
                elements2 = new ArrayList<>();
                do {
                    int indiceN = c2.getColumnIndex("nombre_cientifico");
                    int indicePV = c2.getColumnIndex("provincias");

                    String nombrecientifico = c2.getString(indiceN);
                    String provinciass = c2.getString(indicePV);

                    // Llamada al método getUrlImagen con un listener
                    getUrlImagen(nombrecientifico, new OnImageUrlFetchedListener() {
                        @Override
                        public void onImageUrlFetched(String imagenUrl) {
                            // Se ejecuta cuando el hilo termina y se obtiene la URL de la imagen
                            Log.d("Consulta2", "URL de la imagen: " + imagenUrl);

                            // Agregar el elemento con la URL de la imagen al listado de elementos
                            elements2.add(new ListarElementos(imagenUrl, nombrecientifico, provinciass, "Ver"));

                            // Actualiza el RecyclerView después de agregar los elementos
                            init2();
                        }
                    });

                    Log.d("Consulta", "Provincia encontrada: " + provinciass);

                } while (c2.moveToNext());
                c2.close();
            }
        } catch (Exception e) {
            Log.e("Consulta2", "Error: " + e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void init2() {
        ListAdapter listAdapter2 = new ListAdapter(elements2, this);
        RecyclerView recyclerView2 = findViewById(R.id.listRecycleView);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(listAdapter2);
    }

    public void getUrlImagen(String indiceN, OnImageUrlFetchedListener listener) {
        // Hacer la solicitud en un hilo para evitar bloquear el hilo principal
        new Thread(() -> {
            String imagenUrl = "";  // La URL de la imagen a obtener

            try {
                // Conectar a la página de Wikipedia del pez
                String urlWiki = "https://es.wikipedia.org/wiki/" + indiceN.replace(" ", "_");
                Document doc = Jsoup.connect(urlWiki).get();

                Elements images = doc.select(".mw-file-element");
                String imgUrl = "";
                Log.d("Consulta2", "Imagenes: " + images.size());

                // Buscamos la imagen
                for (Element image : images) {
                    String imageSrc = "https:" + image.attr("src");
                    if (!imageSrc.contains("svg.") && !imageSrc.isEmpty()) {
                        imgUrl = imageSrc;  // Asignamos el URL de la imagen encontrada
                        break;  // Salir del bucle si encontramos la imagen
                    }
                    Log.d("Consulta2", imageSrc);
                }

                // Si se encontró una imagen válida, la cargamos
                if (!imgUrl.isEmpty()) {
                    imagenUrl = imgUrl;
                } else {
                    // Si no se encuentra la imagen, intentamos usar un nombre alternativo
                    Log.d("Consulta2", "Imagen no encontrada en Wikipedia, creando URL alternativo");

                    // Construir la URL alternativa usando solo la primera parte de indiceN
                    String[] nombres = indiceN.split(" ");  // Separar por espacio
                    if (nombres.length > 0) {
                        // Usamos solo la primera palabra del nombre científico
                        String alternativeUrl = "https://es.wikipedia.org/wiki/" + nombres[0].replace(" ", "_");

                        // Ahora intentamos conectarnos a esa nueva URL y obtener la imagen
                        Document docAlternative = Jsoup.connect(alternativeUrl).get();
                        Elements imagesAlternative = docAlternative.select(".mw-file-element");
                        for (Element image : imagesAlternative) {
                            String imageSrc = "https:" + image.attr("src");
                            if (!imageSrc.contains("svg.") && !imageSrc.isEmpty()) {
                                imgUrl = imageSrc;  // Asignamos el URL de la imagen encontrada
                                break;  // Salir del bucle si encontramos la imagen
                            }
                        }

                        // Si se encontró una imagen válida, la cargamos
                        if (!imgUrl.isEmpty()) {
                            imagenUrl = imgUrl;
                        } else {
                            imagenUrl = "vacio";  // Si no se encuentra imagen, asignamos "vacio"
                        }
                        Log.d("Consulta2", "Imagen URL alternativa: " + imagenUrl);
                    } else {
                        imagenUrl = "vacio";  // Si no se puede obtener una URL alternativa, asignamos "vacio"
                    }
                }

            } catch (Exception e) {
                Log.e("Consulta2", "Error al obtener la imagen: " + e.toString());
                imagenUrl = "vacio";  // En caso de excepción, asignar "vacio"
            }

            // Utiliza runOnUiThread para actualizar la UI en el hilo principal
            String finalImagenUrl = imagenUrl;
            runOnUiThread(() -> {
                listener.onImageUrlFetched(finalImagenUrl);  // Llamamos al listener en el hilo principal
            });
        }).start();
    }


    // Interfaz Callback para notificar al hilo principal
    public interface OnImageUrlFetchedListener {
        void onImageUrlFetched(String imagenUrl);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
