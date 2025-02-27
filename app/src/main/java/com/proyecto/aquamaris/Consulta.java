package com.proyecto.aquamaris;

import android.annotation.SuppressLint;
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

        try {
            DBHelper db = new DBHelper(this);
            SQLiteDatabase obj = db.getReadableDatabase();
            province = getIntent().getExtras().getString("PROVINCIA");
            Cursor c = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%" + province + "%'", null);
            Log.d("ValorProvincia", "Provincia: " + province);
            if (c != null && c.moveToFirst()) {
                elements = new ArrayList<>();
                do {

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
}
