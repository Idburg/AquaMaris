package com.proyecto.aquamaris.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.aquamaris.AvisoLegal;
import com.proyecto.aquamaris.Login;
import com.proyecto.aquamaris.R;


public class Prueba3 extends Fragment {
    /** @noinspection deprecation*/
    GoogleSignInClient mGoogleSignInClient;

    public Prueba3() {
        // Required empty public constructor
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //noinspection deprecation
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //noinspection deprecation
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth.getCurrentUser();
        assert user1 != null;

        TextView username = view.findViewById(R.id.nombreuser);
        username.setText(user1.getEmail());

        AppCompatButton cerrar = view.findViewById(R.id.settings_sign_out);
        cerrar.setOnClickListener(view1 -> {
            mAuth.signOut();
            googleSignOut();

            SharedPreferences.Editor editor = requireActivity()
                    .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    .edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(requireActivity(), Login.class);
            startActivity(intent);
            requireActivity().finish();
        });

    }

    public void googleSignOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task ->
                mGoogleSignInClient.revokeAccess().addOnCompleteListener(requireActivity(), task1 -> {}));
    }
}
