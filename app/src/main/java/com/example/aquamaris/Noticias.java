package com.example.aquamaris;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
    private FrameLayout NoticiaPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerView);
        newsImage = findViewById(R.id.newsImageLarge);  // Imagen
        newsTitle = findViewById(R.id.newsTitleLarge);  // Título
        NoticiaPrincipal = findViewById(R.id.imageContainer);
        newsList = new ArrayList<>();

        // Configurar RecyclerView con un LinearLayoutManager (dos columnas)
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
                String href = "";
                String HrefPrincipal="";
                Document doc = Jsoup.connect("https://www.farodevigo.es/mar/").get();
                List<Element> articleElements = doc.select("article");
                Elements articleElements1 = doc.select(".new.over");
                if (articleElements1.isEmpty()) {
                    articleElements1 = doc.select(".new.over.premiun");
                }
                for(Element e : articleElements1){
                    System.out.println(e.id());
                }
                Element firstArticle = articleElements1.first();
                System.out.println("Noticias Principales: "+articleElements1.size());

                if (firstArticle != null) {
                    Elements ima = firstArticle.getElementsByTag("img");
                    String img = ima.attr("src");

                    Elements h1 = firstArticle.getElementsByTag("h1");
                    String title = h1.text();

                    Elements aP = firstArticle.getElementsByTag("a");
                    Element aP_newHeadline = aP.select(".new__headline").first();
                    if (aP_newHeadline != null) {
                        HrefPrincipal = aP_newHeadline.attr("href"); // Obtiene el atributo href
                        System.out.println("Href Principal: " + HrefPrincipal);
                    }

                    // Asignamos los datos al UI (en el hilo principal)
                    String finalHrefPrincipal = HrefPrincipal;
                    runOnUiThread(() -> {
                        newsTitle.setText(title);  // Asignamos el título
                        Glide.with(Noticias.this).load(img).into(newsImage);
                        NoticiaPrincipal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Aquí, pasar el href al WebNews Activity
                                Intent intent = new Intent(Noticias.this, WebNews.class);
                                intent.putExtra("url", finalHrefPrincipal);  // Pasamos el href a WebNews
                                startActivity(intent);
                            }
                        });
                    });

                } else {
                   System.out.println("Noticia principal vacia");
                }

                // Asegúrate de que setOnClickListener se ejecuta en el hilo principal

                for (Element elemento : articleElements) {
                    Elements imagen = elemento.getElementsByTag("source");
                    String img = imagen.attr("srcset");
                    Elements h2 = elemento.getElementsByTag("h2");
                    Elements a = elemento.getElementsByTag("a");
                    Element a_newHeadline = a.select(".new__headline").first(); // Selecciona por clase
                    String title = h2.text();

                    // Verifica si el elemento <a> con la clase "new__headline" existe
                    if (a_newHeadline != null) {
                        href = a_newHeadline.attr("href"); // Obtiene el atributo href
                        System.out.println("Href: " + href);
                    }

                    // Agrega los artículos a la lista si hay imagen y título
                    if (!imagen.isEmpty() && !h2.isEmpty() && i < 8) {
                        newsList.add(new NewsItem(title, img, href));
                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                        i++;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    newsList.add(new NewsItem("Error al cargar noticias", "", ""));
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();
    }
}
