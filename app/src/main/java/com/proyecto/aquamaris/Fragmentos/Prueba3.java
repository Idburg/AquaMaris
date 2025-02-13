package com.proyecto.aquamaris.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.aquamaris.NombrePeces;
import com.proyecto.aquamaris.R;

import java.util.List;

public class Prueba3 extends Fragment {

    private RecyclerView lista;
    private List<NombrePeces> Peces;

    public Prueba3() {
        // Required empty public constructor
    }

    public static Prueba3 newInstance(String param1, String param2) {
        Prueba3 fragment = new Prueba3();
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
        return inflater.inflate(R.layout.prueba3, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        lista = view.findViewById(R.id.nombrepeces);



    }

}
