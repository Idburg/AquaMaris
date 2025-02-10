package com.proyecto.aquamaris;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aquamaris.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Noticias extends AppCompatActivity {
    private ImageView newsImage;
    private TextView newsTitle;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsItem> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerView);
        newsImage = findViewById(R.id.newsImageLarge);  // Imagen
        newsTitle = findViewById(R.id.newsTitleLarge);  // Título
        newsList = new ArrayList<>();

        // Configurar RecyclerView con un LinearLayoutManager (una sola columna)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columnas
        adapter = new NewsAdapter(newsList, this);
        recyclerView.setAdapter(adapter);

        // Cargar noticias mediante scraping
        loadNewsFromScraping();
    }

    private void loadNewsFromScraping() {
        new Thread(() -> {
            try {
                int i = 0;
                Document doc = Jsoup.connect("https://www.farodevigo.es/mar/").get();
                List<Element> articleElements = doc.select("article");
                Elements articleElements1 = doc.getElementsByClass("new over premium");
                Element firstArticle = articleElements1.first();
                if (firstArticle != null) {
                    Elements ima = firstArticle.getElementsByTag("img");
                    String img = ima.attr("src");

                    Elements h1 = firstArticle.getElementsByTag("h1");
                    String title = h1.text();

                    // Asignamos los datos al UI (en el hilo principal)
                    runOnUiThread(() -> {
                        newsTitle.setText(title);  // Asignamos el título
                        // Aquí se podría cargar la imagen si tienes una librería como Glide o Picasso
                        Glide.with(Noticias.this).load(img).into(newsImage);
                    });
                }
                else{
                    String title="MAL";
                    newsTitle.setText(title);
                }

                for (Element elemento : articleElements) {
                    Elements imagen = elemento.getElementsByTag("source");
                    String img = imagen.attr("srcset");

                    Elements h2 = elemento.getElementsByTag("h2");
                    String title = h2.text();

                    if (!imagen.isEmpty() && !h2.isEmpty() && i < 8) {
                        newsList.add(new NewsItem(title, img));
                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                        i++;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    newsList.add(new NewsItem("Error al cargar noticias", ""));
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();
    }
}
