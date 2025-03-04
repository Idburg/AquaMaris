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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.aquamaris.Login;
import com.proyecto.aquamaris.NombrePeces;
import com.proyecto.aquamaris.NombrePecesAdapter;
import com.proyecto.aquamaris.R;
import com.proyecto.aquamaris.Splash;
import com.proyecto.aquamaris.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Prueba3 extends Fragment {

    String province;
    TextView resultado2;
    List<NombrePeces> pecesList;
    String formato = "";
    GoogleSignInClient mGoogleSignInClient;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        TextView info = view.findViewById(R.id.infpersonal);
        info.setText(mAuth.getCurrentUser().getDisplayName());

        TextView user = view.findViewById(R.id.nombreuser);
        user.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        AppCompatButton cerrar = view.findViewById(R.id.settings_sign_out);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();

                mAuth.signOut();
                googleSignOut();

                FirebaseDatabase.getInstance().setPersistenceEnabled(false);
                Splash.pleaseDestroy = true;

                SharedPreferences.Editor editor = requireActivity()
                        .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        .edit();
                editor.clear();
                editor.apply();



                Intent intent = new Intent(requireActivity(), Login.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

    }

    public void googleSignOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            mGoogleSignInClient.revokeAccess().addOnCompleteListener(requireActivity(), task1 -> {
            });
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
