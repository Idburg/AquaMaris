package com.proyecto.aquamaris.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.proyecto.aquamaris.R;

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
        newsTitle = view.findViewById(R.id.newsTitleLarge);  // Título
        newsList = new ArrayList<>();

        // Configurar RecyclerView con un LinearLayoutManager (una sola columna)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columnas
        adapter = new NewsAdapter(newsList, getContext());
        recyclerView.setAdapter(adapter);

        loadNewsFromScraping();
    }


    private void loadNewsFromScraping() throws NullPointerException{
    new Thread(() -> {
        try {
            int i = 0;
            Document doc = Jsoup.connect("https://www.farodevigo.es/mar/").get();
            List<Element> articleElements = doc.select("article");
            Elements articleElements1 = doc.getElementsByClass("new over");
            Element firstArticle = articleElements1.first();
            if (firstArticle != null) {
                Elements ima = firstArticle.getElementsByTag("img");
                String img = ima.attr("src");

                Elements h1 = firstArticle.getElementsByTag("h1");
                String title = h1.text();

                    // Asignamos los datos al UI (en el hilo principal)
                this.getActivity().runOnUiThread(() -> {
                    newsTitle.setText(title);  // Asignamos el título
                            // Aquí se podría cargar la imagen si tienes una librería como Glide o Picasso
                    Glide.with(requireContext()).load(img).into(newsImage);
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
                     newsList.add(new NewsItem(title, img, ""));
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

