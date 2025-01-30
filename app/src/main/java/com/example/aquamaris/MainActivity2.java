package com.example.aquamaris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    EditText et;
    Button b3;
    String provincia;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et = findViewById(R.id.editTextText);
        //b3 = findViewById(R.id.button3);

        // Crear una lista de provincias
        List<Province> provinciaList = new ArrayList<>();
        provinciaList.add(new Province(R.drawable.alava, "Álava"));
        provinciaList.add(new Province(R.drawable.albacete, "Albacete"));
        provinciaList.add(new Province(R.drawable.alicante, "Alicante"));
        provinciaList.add(new Province(R.drawable.almeria, "Almería"));
        provinciaList.add(new Province(R.drawable.asturias, "Asturias"));
        provinciaList.add(new Province(R.drawable.avila, "Ávila"));
        provinciaList.add(new Province(R.drawable.badajoz, "Badajoz"));
        provinciaList.add(new Province(R.drawable.barcelona, "Barcelona"));
        provinciaList.add(new Province(R.drawable.burgos, "Burgos"));
        provinciaList.add(new Province(R.drawable.cabrera, "Cabrera"));
        provinciaList.add(new Province(R.drawable.caceres, "Cáceres"));
        provinciaList.add(new Province(R.drawable.cadiz, "Cádiz"));
        provinciaList.add(new Province(R.drawable.cantabria, "Cantabria"));
        provinciaList.add(new Province(R.drawable.castellon, "Castellón"));
        provinciaList.add(new Province(R.drawable.ciudadreal, "Ciudad Real"));
        provinciaList.add(new Province(R.drawable.cordoba, "Córdoba"));
        provinciaList.add(new Province(R.drawable.cuenca, "Cuenca"));
        provinciaList.add(new Province(R.drawable.elhierro, "El Hierro"));
        provinciaList.add(new Province(R.drawable.formentera, "Formentera"));
        provinciaList.add(new Province(R.drawable.fuerteventura, "Fuerteventura"));
        provinciaList.add(new Province(R.drawable.gerona, "Gerona"));
        provinciaList.add(new Province(R.drawable.gipuzkoa, "Gipuzkoa"));
        provinciaList.add(new Province(R.drawable.granada, "Granada"));
        provinciaList.add(new Province(R.drawable.grancanaria, "Gran Canaria"));
        provinciaList.add(new Province(R.drawable.guadalajara, "Guadalajara"));
        provinciaList.add(new Province(R.drawable.huelva, "Huelva"));
        provinciaList.add(new Province(R.drawable.huesca, "Huesca"));
        provinciaList.add(new Province(R.drawable.ibiza, "Ibiza"));
        provinciaList.add(new Province(R.drawable.jaen, "Jaén"));
        provinciaList.add(new Province(R.drawable.lacorunha, "La Coruña"));
        provinciaList.add(new Province(R.drawable.lagomera, "La Gomera"));
        provinciaList.add(new Province(R.drawable.lanzarote, "Lanzarote"));
        provinciaList.add(new Province(R.drawable.lapalma, "La Palma"));
        provinciaList.add(new Province(R.drawable.larioja, "La Rioja"));
        provinciaList.add(new Province(R.drawable.leon, "León"));
        provinciaList.add(new Province(R.drawable.lerida, "Lérida"));
        provinciaList.add(new Province(R.drawable.lugo, "Lugo"));
        provinciaList.add(new Province(R.drawable.madrid, "Madrid"));
        provinciaList.add(new Province(R.drawable.malaga, "Málaga"));
        provinciaList.add(new Province(R.drawable.mallorca, "Mallorca"));
        provinciaList.add(new Province(R.drawable.menorca, "Menorca"));
        provinciaList.add(new Province(R.drawable.murcia, "Murcia"));
        provinciaList.add(new Province(R.drawable.navarra, "Navarra"));
        provinciaList.add(new Province(R.drawable.ourense, "Ourense"));
        provinciaList.add(new Province(R.drawable.palencia, "Palencia"));
        provinciaList.add(new Province(R.drawable.pontevedra, "Pontevedra"));
        provinciaList.add(new Province(R.drawable.salamanca, "Salamanca"));
        provinciaList.add(new Province(R.drawable.sevilla, "Sevilla"));
        provinciaList.add(new Province(R.drawable.soria, "Soria"));
        provinciaList.add(new Province(R.drawable.tarragona, "Tarragona"));
        provinciaList.add(new Province(R.drawable.tenerife, "Tenerife"));
        provinciaList.add(new Province(R.drawable.teruel, "Teruel"));
        provinciaList.add(new Province(R.drawable.toledo, "Toledo"));
        provinciaList.add(new Province(R.drawable.valencia, "Valencia"));
        provinciaList.add(new Province(R.drawable.valladolid, "Valladolid"));
        provinciaList.add(new Province(R.drawable.vizcaya, "Vizcaya"));
        provinciaList.add(new Province(R.drawable.zamora, "Zamora"));
        provinciaList.add(new Province(R.drawable.zaragoza, "Zaragoza"));



        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerViewAdapter = new RecyclerViewAdapter(provinciaList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);


        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        FloatingActionButton myfab = findViewById(R.id.fab);


        myfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provincia = et.getText().toString();
                Intent intent = new Intent(MainActivity2.this, Consulta.class);
                intent.putExtra("PROVINCIA", provincia);
                startActivity(intent);
            }
        });


        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity2.this, "Menu clicked", Toast.LENGTH_SHORT).show();

            }


        });


        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.heart)
                {
                    Toast.makeText(MainActivity2.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                }
                if(id == R.id.search)
                {
                    Toast.makeText(MainActivity2.this, "Beginning search", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        /*
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provincia = et.getText().toString();
                Intent intent = new Intent(MainActivity2.this, Consulta.class);
                intent.putExtra("PROVINCIA", provincia);
                startActivity(intent);
            }
        });
    */

    }

}