package com.example.aquamaris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
/*
    EditText et;
    Button b3;
    String provinciaa;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    int []arr={R.drawable.alava,R.drawable.albacete,R.drawable.alicante,R.drawable.almeria,R.drawable.asturias,R.drawable.avila,
            R.drawable.badajoz,R.drawable.barcelona,R.drawable.burgos,R.drawable.cabrera,R.drawable.caceres,R.drawable.cadiz,R.drawable.cantabria,
            R.drawable.castellon,R.drawable.ciudadreal,R.drawable.cordoba,R.drawable.cuenca,R.drawable.elhierro,R.drawable.formentera,
            R.drawable.fuerteventura,R.drawable.gerona,R.drawable.gipuzkoa,R.drawable.granada,R.drawable.grancanaria,R.drawable.guadalajara,
            R.drawable.huelva,R.drawable.huesca,R.drawable.ibiza,R.drawable.jaen,R.drawable.lacorunha,R.drawable.lagomera,R.drawable.lanzarote,
            R.drawable.lapalma,R.drawable.larioja,R.drawable.leon,R.drawable.lerida,R.drawable.lugo,R.drawable.madrid,R.drawable.malaga,R.drawable.mallorca,
            R.drawable.menorca,R.drawable.murcia,R.drawable.navarra,R.drawable.ourense,R.drawable.palencia,R.drawable.pontevedra,R.drawable.salamanca,
            R.drawable.sevilla,R.drawable.soria,R.drawable.tarragona,R.drawable.tenerife,R.drawable.teruel,R.drawable.toledo,
            R.drawable.valencia,R.drawable.valladolid,R.drawable.vizcaya,R.drawable.zamora,R.drawable.zaragoza};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        b3 = findViewById(R.id.button3);
        et = findViewById(R.id.editTextText);


        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(arr);

        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setHasFixedSize(true);



        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provinciaa = et.getText().toString();
                Intent intent = new Intent(MainActivity2.this, Consulta.class);

                intent.putExtra("PROVINCIA",provinciaa);

                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }*/

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

        b3 = findViewById(R.id.button3);
        et = findViewById(R.id.editTextText);

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
        // Agrega las demás provincias de forma similar

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Configurar el adaptador con la lista de provincias
        recyclerViewAdapter = new RecyclerViewAdapter(provinciaList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provincia = et.getText().toString();
                Intent intent = new Intent(MainActivity2.this, Consulta.class);
                intent.putExtra("PROVINCIA", provincia);
                startActivity(intent);
            }
        });
    }

}