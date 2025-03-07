package com.proyecto.aquamaris.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.proyecto.aquamaris.Consulta;
import com.proyecto.aquamaris.Consulta2;
import com.proyecto.aquamaris.MapsActivity;
import com.proyecto.aquamaris.Province;
import com.proyecto.aquamaris.R;
import com.proyecto.aquamaris.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Prueba2 extends Fragment {
    EditText et;
    ImageButton b3;
    String provincia;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    public Prueba2() {
        // Required empty public constructor
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et = view.findViewById(R.id.editTextText);
        b3 = view.findViewById(R.id.button3);

        // Crear una lista de provincias
        List<Province> provinciaList = new ArrayList<>();
        provinciaList.add(new Province(R.drawable.lacorunha, "A Coruña"));
        provinciaList.add(new Province(R.drawable.alava, "Álava"));
        provinciaList.add(new Province(R.drawable.albacete, "Albacete"));
        provinciaList.add(new Province(R.drawable.alicante, "Alicante"));
        provinciaList.add(new Province(R.drawable.almeria, "Almería"));
        provinciaList.add(new Province(R.drawable.asturias, "Asturias"));
        provinciaList.add(new Province(R.drawable.avila, "Ávila"));
        provinciaList.add(new Province(R.drawable.badajoz, "Badajoz"));
        provinciaList.add(new Province(R.drawable.barcelona, "Barcelona"));
        provinciaList.add(new Province(R.drawable.burgos, "Burgos"));
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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerViewAdapter = new RecyclerViewAdapter(provinciaList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);


        BottomAppBar bottomAppBar = view.findViewById(R.id.bottom_app_bar);
        FloatingActionButton myfab = view.findViewById(R.id.fab);


        b3.setOnClickListener(v -> {
            boolean isValidProvince = false;
            provincia = et.getText().toString().toLowerCase().replace(" ", "");

            try {
                if (provincia.contains("alava") || provincia.contains("almeria") || provincia.contains("avila") || provincia.contains("caceres")
                        || provincia.contains("cadiz") || provincia.contains("castellon") || provincia.contains("cordoba") || provincia.contains("gipuzcoa")
                        || provincia.contains("jaen") || provincia.contains("leon") || provincia.contains("lerida") || provincia.contains("malaga")
                        || provincia.contains("acoruna")) {
                    provincia = provincia.replace("alava", "Álava");
                    provincia = provincia.replace("almeria", "Almería");
                    provincia = provincia.replace("avila", "Ávila");
                    provincia = provincia.replace("caceres", "Cáceres");
                    provincia = provincia.replace("cadiz", "Cádiz");
                    provincia = provincia.replace("castellon", "Castellón");
                    provincia = provincia.replace("cordoba", "Córdoba");
                    provincia = provincia.replace("gipuzcoa", "Gipúzcoa");
                    provincia = provincia.replace("jaen", "Jaén");
                    provincia = provincia.replace("leon", "León");
                    provincia = provincia.replace("lerida", "Lérida");
                    provincia = provincia.replace("malaga", "Málaga");
                    provincia = provincia.replace("acoruna","A Coruña");
                } else {
                    char firstLetter = provincia.charAt(0);
                    provincia = provincia.replace(provincia.charAt(0), Character.toUpperCase(firstLetter));
                }


                for (Province prov : provinciaList) {
                    if (Objects.equals(prov.getNombre(), provincia)) {
                        isValidProvince = true;
                        break;
                    }
                }

                if (!provincia.isEmpty() && isValidProvince) {
                    Intent intent = new Intent(getContext(), Consulta.class);
                    intent.putExtra("PROVINCIA", provincia);
                    startActivity(intent);
                } else {
                    et.setText(null);
                }
            }
            catch (Exception e) {
                Log.d("SearchBar","Error: "+e);
            }

        });

        myfab.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), MapsActivity.class);
            startActivity(intent);
        });

        bottomAppBar.setNavigationOnClickListener(view12 -> {
            Intent intent = new Intent(getContext(), Consulta2.class);
            intent.putExtra("PROVINCIA", provincia);
            startActivity(intent);
        });

    }
}

