package com.proyecto.aquamaris.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.aquamaris.Login;
import com.proyecto.aquamaris.NombrePeces;
import com.proyecto.aquamaris.NombrePecesAdapter;
import com.proyecto.aquamaris.R;
import com.proyecto.aquamaris.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Prueba3 extends Fragment {

    String province;
    TextView resultado2;
    List<NombrePeces> pecesList;
    String formato = "";

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
        return inflater.inflate(R.layout.activity_pantalla_ajustes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        try {
            DBHelper db = new DBHelper(getContext());
            SQLiteDatabase obj = db.getReadableDatabase();
            Cursor c = obj.rawQuery("SELECT nombre_cientifico FROM peces", null);

            //Cursor c = obj.rawQuery("SELECT * FROM peces WHERE LOWER(provincias) LIKE LOWER(?)", new String[]{"%" + provincia.toLowerCase() + "%"});
            if(c != null && c.moveToFirst())
            {
                pecesList = new ArrayList<>();
                do{

                    int indiceN = c.getColumnIndex("nombre_cientifico");

                    String nombrecientifico = c.getString(indiceN);

                    if (nombrecientifico != null && !nombrecientifico.trim().isEmpty()) {

                        nombrecientifico = nombrecientifico.trim();

                        String[] palabras = nombrecientifico.split("\\s+");

                        if (palabras.length > 1) {
                            String formato = palabras[0]+"_"+palabras[1].toLowerCase();
                            pecesList.add(new NombrePeces(formato));
                        }
                    }

                }while(c.moveToNext());
                c.close();
            }

            //init3(view);
        }catch(Exception e)
        {
            System.out.println("Error");
        }

        AppCompatButton cerrar = view.findViewById(R.id.settings_sign_out);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                SharedPreferences.Editor editor = requireActivity()
                        .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        .edit();
                editor.clear();
                editor.apply();

                FirebaseDatabase.getInstance().setPersistenceEnabled(false);

                Intent intent = new Intent(requireActivity(), Login.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

    }
/*
    public void init3(View view)
    {

        NombrePecesAdapter npa = new NombrePecesAdapter(pecesList, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.listRecycleView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(npa);
    }
*/
}
