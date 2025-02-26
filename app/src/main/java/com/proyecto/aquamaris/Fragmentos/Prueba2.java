package com.proyecto.aquamaris.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.aquamaris.Consulta;
import com.proyecto.aquamaris.Consulta2;
import com.proyecto.aquamaris.MapsActivity;
import com.proyecto.aquamaris.R;
import com.proyecto.aquamaris.Province;
import com.proyecto.aquamaris.RecyclerViewAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Prueba2 extends Fragment {
    EditText et;
    ImageButton b3;
    String provincia;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    public Prueba2() {
        // Required empty public constructor
    }

    public static Prueba2 newInstance(String param1, String param2) {
        Prueba2 fragment = new Prueba2();
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
        return inflater.inflate(R.layout.prueba2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        et = view.findViewById(R.id.editTextText);
        b3 = view.findViewById(R.id.button3);

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
        provinciaList.add(new Province(R.drawable.gerona, "Girona"));
        provinciaList.add(new Province(R.drawable.gipuzkoa, "Gipúzcoa"));
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

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerViewAdapter = new RecyclerViewAdapter(provinciaList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);


        BottomAppBar bottomAppBar = view.findViewById(R.id.bottom_app_bar);
        FloatingActionButton myfab = view.findViewById(R.id.fab);


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provincia = et.getText().toString();
                Intent intent = new Intent(getContext(), MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("PROVINCIA",provincia);
                startActivity(intent);

            }
        });

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Consulta2.class);
                intent.putExtra("PROVINCIA", provincia);
                startActivity(intent);
            }


        });

    }
}

