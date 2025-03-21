package com.proyecto.aquamaris;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ui.main.SectionsPagerAdapter;

public class Noticias extends AppCompatActivity {
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = binding.viewPager;
        ViewPager viewPager1 = findViewById(R.id.view_pager);
        viewPager1.setAdapter(sectionsPagerAdapter);


        BottomNavigationView mybottomNavView = findViewById(R.id.bottom_navigation);
        mybottomNavView.setItemIconTintList(null);

        //noinspection deprecation
        mybottomNavView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_selected},
                            new int[]{}
                    },
                    new int[]{
                            ContextCompat.getColor(Noticias.this, R.color.black),
                            ContextCompat.getColor(Noticias.this, R.color.white)
                    }
            );


            if (id == R.id.noticias) {
                mybottomNavView.setItemTextColor(colorStateList);
                viewPager1.setCurrentItem(0);
            } else if (id == R.id.provincias) {
                mybottomNavView.setItemTextColor(colorStateList);
                viewPager1.setCurrentItem(1);
            } else if (id == R.id.ajustes) {
                mybottomNavView.setItemTextColor(colorStateList);
                viewPager1.setCurrentItem(2);
            }

            return true;
        });

        mybottomNavView.setSelectedItemId(R.id.noticias);

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
}
