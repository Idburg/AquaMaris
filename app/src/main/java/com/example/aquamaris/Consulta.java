package com.example.aquamaris;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aquamaris.db.DBHelper;

public class Consulta extends AppCompatActivity {

    TextView resultado;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) throws IllegalStateException{
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consulta);
        resultado = findViewById(R.id.textView2);

        try {
            DBHelper db = new DBHelper(this);
            SQLiteDatabase obj = db.getReadableDatabase();
            Cursor c = obj.rawQuery("SELECT * FROM peces WHERE provincias LIKE '%Huelva%'",null);
            if(c != null)
            {
                c.moveToFirst();
                do{
                    //int indiceP = c.getColumnIndex("pais");
                    int indiceN = c.getColumnIndex("nombre_científico");
                    //int indiceF = c.getColumnIndex("familia");
                    //int indiceL = c.getColumnIndex("localizacion");
                    //int indicePV = c.getColumnIndex("provincias");

                    //String pais = c.getString(indiceP);
                    String nombrecientifico = c.getString(indiceN);
                    //String familia = c.getString(indiceF);
                    //String localizacion = c.getString(indiceL);
                    //String provincias = c.getString(indicePV);


                    resultado.setText(resultado.getText()+" "+nombrecientifico+"\n");
                    //" "+pais+
                    //" "+provincias+" "+familia+" "+localizacion+
                    //
                }while(c.moveToNext());
            }


            db.close();

        }catch(Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}