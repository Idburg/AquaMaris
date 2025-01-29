package com.example.aquamaris;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aquamaris.NewsItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Noticias extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsItem> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerView);
        newsList = new ArrayList<>();

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columnas
        adapter = new NewsAdapter(newsList, this);
        recyclerView.setAdapter(adapter);

        // Cargar noticias mediante scraping
        loadNewsFromScraping();
    }

    private void loadNewsFromScraping() {
        new Thread(() -> {
            try {
                int i=0;
                // Conectar a la página web
                Document doc = Jsoup.connect("https://www.farodevigo.es/mar/").get();

                // Seleccionar elementos de imágenes y titulares
                List<Element> articleElements = doc.select("article");
                for ( Element Elemento : articleElements){
                    Elements imagen = Elemento.getElementsByTag("source");
                    String img = imagen.attr("srcset");
                        Elements h2 = Elemento.getElementsByTag("h2");
                        String title = h2.text();
                    if(!imagen.isEmpty()&&!h2.isEmpty()&&i<5) {
                        newsList.add(new NewsItem(title, img));
                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                        i++;
                    }

                }

                } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    // Mostrar mensaje de error
                    newsList.add(new NewsItem("Error al cargar noticias", ""));
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();
    }
}