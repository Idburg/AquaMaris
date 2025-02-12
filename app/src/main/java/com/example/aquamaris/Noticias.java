package com.example.aquamaris;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ui.main.SectionsPagerAdapter;

public class Noticias extends AppCompatActivity {
    private ImageView newsImage;
    private TextView newsTitle;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsItem> newsList;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = binding.viewPager;
        ViewPager viewPager1 = findViewById(R.id.view_pager);
        viewPager1.setAdapter(sectionsPagerAdapter);

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

        BottomNavigationView mybottomNavView = findViewById(R.id.bottom_navigation);
        mybottomNavView.setItemIconTintList(null);



        /*
        // crear badges
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) mybottomNavView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        LayoutInflater.from(this)
                .inflate(R.layout.layout_badge, itemView, true);
        */


        mybottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.noticias && item.isChecked()) {

                    //item.setChecked(true);
                    Toast.makeText(Noticias.this, "Noticias", Toast.LENGTH_SHORT).show();
                    //removeBadge(mybottomNavView,item.getItemId());
                    viewPager1.setCurrentItem(0);

                }

                if (id == R.id.provincias) {
                    //item.setChecked(true);
                    Intent intent = new Intent(Noticias.this, MainActivity2.class);
                    startActivity(intent);
                    //Toast.makeText(Noticias.this, "Add clicked.", Toast.LENGTH_SHORT).show();
                    //removeBadge(mybottomNavView,item.getItemId());
                    viewPager1.setCurrentItem(1);

                }
                if(id == R.id.ajustes) {
                    //item.setChecked(true);
                    Toast.makeText(Noticias.this, "Ajustes", Toast.LENGTH_SHORT).show();
                    //removeBadge(mybottomNavView,item.getItemId());
                    viewPager1.setCurrentItem(2);
                }

                return true;
            }

        });

        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    mybottomNavView.getMenu().getItem(0).setChecked(false);
                    mybottomNavView.getMenu().getItem(position).setChecked(true);
                    //removeBadge(mybottomNavView, mybottomNavView.getMenu().getItem(position).getItemId());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.news), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadNewsFromScraping() {
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
