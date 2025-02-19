package com.proyecto.aquamaris.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.proyecto.aquamaris.NewsAdapter;
import com.proyecto.aquamaris.NewsItem;
import com.proyecto.aquamaris.Noticias;
import com.proyecto.aquamaris.R;
import com.proyecto.aquamaris.WebNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ui.main.SectionsPagerAdapter;

public class Prueba1 extends Fragment {

    private ImageView newsImage;
    private TextView newsTitle;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsItem> newsList;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private MenuItem prevMenuItem;
    private FrameLayout NoticiaPrincipal;

    public Prueba1() {
        // Required empty public constructor
    }

    public static Prueba1 newInstance() {
        Prueba1 fragment = new Prueba1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.prueba1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        newsImage = view.findViewById(R.id.newsImageLarge);  // Imagen
        newsTitle = view.findViewById(R.id.newsTitleLarge);// Título
        NoticiaPrincipal = view.findViewById(R.id.imageContainer);
        newsList = new ArrayList<>();

        // Configurar RecyclerView con un LinearLayoutManager (una sola columna)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columnas
        adapter = new NewsAdapter(newsList, getContext());
        recyclerView.setAdapter(adapter);

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
                    this.getActivity().runOnUiThread(() -> {
                        newsTitle.setText(title);  // Asignamos el título
                        Glide.with(getContext()).load(img).into(newsImage);
                        NoticiaPrincipal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Aquí, pasar el href al WebNews Activity
                                Intent intent = new Intent(getContext(), WebNews.class);
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
                        this.getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                        i++;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                this.getActivity().runOnUiThread(() -> {
                    newsList.add(new NewsItem("Error al cargar noticias", "", ""));
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();
    }
}

