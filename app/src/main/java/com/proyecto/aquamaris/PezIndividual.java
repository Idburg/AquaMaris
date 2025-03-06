package com.proyecto.aquamaris;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class PezIndividual extends AppCompatActivity {
    private TextView Info_pez;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pez_individual);

        //Barra volver
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);  // Establece el Toolbar como ActionBar

        // Configura la ActionBar y habilita la flecha "volver"
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Habilita la flecha de "volver"
            getSupportActionBar().setDisplayShowHomeEnabled(true);  // Asegúrate de que el ícono de la home se vea
        }
        // Iniciamos los elementos de la interfaz
        ImageView PezImages = findViewById(R.id.PezImages);
        TextView TituloPez = findViewById(R.id.TituloPez);
        Info_pez = findViewById(R.id.Info_pez);
        String nombrepez = getIntent().getStringExtra("PEZ");
        assert nombrepez != null;
        String titulo = nombrepez.replace("_", " ");
        TituloPez.setText(titulo);

        String language = Locale.getDefault().getLanguage();
        // Creamos un hilo para hacer la solicitud de red en segundo plano
        new Thread(() -> {

            Document doc;
            try {
                // Realizamos la conexión a la página de Wikipedia del pez
                doc = Jsoup.connect("https://"+language+".wikipedia.org/wiki/" + nombrepez).get();
                System.out.println("https://"+language+".wikipedia.org/wiki/" + nombrepez);

                // Extraemos los párrafos de la página pero excluimos las tablas
                Elements paragraphs = doc.select("p:not(table p)");
                Elements images = doc.select(".mw-file-element");
                String imgUrl = "";

                // Recorremos las imágenes para obtener una válida
                for (Element image : images) {
                    String imageSrc = "https:" + image.attr("src");

                    // Verificamos el tamaño de la imagen
                    Bitmap bitmap = Glide.with(PezIndividual.this)
                            .asBitmap()
                            .load(imageSrc)
                            .submit()
                            .get(); // Bloquea y espera la carga de la imagen

                    // Si la imagen es válida, la seleccionamos
                    if (bitmap.getWidth() >= 100 && bitmap.getHeight() >= 100) {
                        imgUrl = imageSrc;
                        break;
                    }
                }

                // Si se encontró una imagen válida, la cargamos
                if (!imgUrl.isEmpty()) {
                    String finalImgUrl = imgUrl;
                    runOnUiThread(() -> Glide.with(PezIndividual.this)
                            .load(finalImgUrl)
                            .centerCrop()
                            .into(PezImages));
                    System.out.println("Imagen: " + imgUrl);
                } else {
                    runOnUiThread(() -> Glide.with(PezIndividual.this)
                            .load(R.drawable.noimage)
                            .centerCrop()
                            .into(PezImages));  // Cargar la imagen en el ImageView
                    System.out.println("Imagen: noImage");
                }

                // Variables para el texto
                StringBuilder paragraphText = new StringBuilder();
                int i = 0;

// Verificamos que haya al menos un párrafo
                if (!paragraphs.isEmpty()) {
                    paragraphText = new StringBuilder(paragraphs.get(i).text());
                    System.out.println(paragraphText);

                    // Añadimos más párrafos si el primero tiene menos de 100 caracteres
                    while (paragraphText.length() < 600 && i + 1 < paragraphs.size()) {
                        i++;
                        paragraphText.append("\n\n").append(paragraphs.get(i).text());
                    }
                }

// Actualizamos el TextView con el texto truncado o completo en el hilo principal
                String finalParagraphText = paragraphText.toString();
                System.out.println(finalParagraphText.length());
                runOnUiThread(() -> Info_pez.setText(finalParagraphText));


            } catch (IOException | InterruptedException | ExecutionException e) {
                try {
                    // Modificamos el nombre del pez en caso de error
                    String[] ppez = nombrepez.split("_");

                    // Usamos el primer elemento (ppez[0]) si no hay segundo elemento (ppez[1])
                    String pezParaBuscar = ppez.length > 1 ? ppez[1] : ppez[0];

                    // Realizamos la conexión con el nombre del pez
                    doc = Jsoup.connect("https://"+language+".wikipedia.org/wiki/" + pezParaBuscar).get();
                    Elements paragraphs = doc.select("p:not(table p)");
                    Elements images = doc.select(".mw-file-element");
                    String imgUrl = "";

                    // Buscamos la imagen
                    for (Element image : images) {
                        String imageSrc = "https:" + image.attr("src");

                        // Verificamos el tamaño de la imagen
                        Bitmap bitmap = Glide.with(PezIndividual.this)
                                .asBitmap()
                                .load(imageSrc)
                                .submit()
                                .get(); // Bloquea y espera la carga de la imagen

                        // Si la imagen es válida, la seleccionamos
                        if (bitmap.getWidth() >= 100 && bitmap.getHeight() >= 100) {
                            imgUrl = imageSrc;
                            break;
                        }
                    }

                    // Si se encontró una imagen válida, la cargamos
                    if (!imgUrl.isEmpty()) {
                        String finalImgUrl = imgUrl;
                        runOnUiThread(() -> Glide.with(PezIndividual.this)
                                .load(finalImgUrl)
                                .centerCrop()
                                .into(PezImages));
                    }
                    else {
                        runOnUiThread(() -> Glide.with(PezIndividual.this)
                                .load(R.drawable.noimage) // Aquí cargamos la imagen desde los recursos (no desde PezImages)
                                .centerCrop()
                                .into(PezImages));  // Cargar la imagen en el ImageView
                        System.out.println("Imagen: noImage");
                    }

                    // Variables para el texto
                    StringBuilder paragraphText = new StringBuilder();
                    int i = 0;

// Verificamos que haya al menos un párrafo
                    if (!paragraphs.isEmpty()) {
                        paragraphText = new StringBuilder(paragraphs.get(i).text());
                        System.out.println(paragraphText);

                        // Añadimos más párrafos si el primero tiene menos de 100 caracteres
                        while (paragraphText.length() < 600 && i + 1 < paragraphs.size()) {
                            i++;
                            paragraphText.append("\n\n").append(paragraphs.get(i).text());
                        }
                    }

// Actualizamos el TextView con el texto truncado o completo en el hilo principal
                    String finalParagraphText = paragraphText.toString();
                    System.out.println(finalParagraphText.length());
                    runOnUiThread(() -> Info_pez.setText(finalParagraphText));

                } catch (IOException | InterruptedException | ExecutionException x) {

                        // Modificamos el fondo de la actividad a azul
                        runOnUiThread(() -> {
                            //noinspection deprecation
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                            // Cambiamos el título y el texto
                            TituloPez.setText(R.string.sorry_mssg);
                            Info_pez.setText(R.string.info_not_found);
                            runOnUiThread(() -> Glide.with(PezIndividual.this)
                                    .load(R.drawable.peztriste) // Aquí cargamos la imagen desde los recursos (no desde PezImages)
                                    .centerCrop()
                                    .into(PezImages));  // Cargar la imagen en el ImageView
                            System.out.println("Imagen: noImage");
                        });

                        // El resto del código sigue igual...
                        // Aquí podrías agregar un mensaje o log para un error específico

                }


            }
        }).start(); // Inicia el hilo

        // Ajuste de padding para los system bars (barras del sistema)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Finaliza la actividad actual para que no quede en el stack
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
