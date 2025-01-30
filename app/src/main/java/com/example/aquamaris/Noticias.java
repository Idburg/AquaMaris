package com.example.aquamaris;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Noticias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_noticias);

        BottomNavigationView mybottomNavView = findViewById(R.id.bottom_navigation);
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
                if (id == R.id.likes) {

                    item.setChecked(true);
                    Toast.makeText(Noticias.this, "Likes clicked.", Toast.LENGTH_SHORT).show();
                    //removeBadge(mybottomNavView,item.getItemId());

                }
                if (id == R.id.add) {
                    item.setChecked(true);
                    Intent intent = new Intent(Noticias.this, MainActivity2.class);
                    startActivity(intent);
                    //Toast.makeText(Noticias.this, "Add clicked.", Toast.LENGTH_SHORT).show();
                    //removeBadge(mybottomNavView,item.getItemId());

                }
                if(id == R.id.browse) {
                    item.setChecked(true);
                    Toast.makeText(Noticias.this, "Browse clicked.", Toast.LENGTH_SHORT).show();
                    //removeBadge(mybottomNavView,item.getItemId());

                }

                return false;
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}