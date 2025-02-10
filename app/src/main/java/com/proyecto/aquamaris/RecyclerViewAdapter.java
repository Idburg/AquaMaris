package com.proyecto.aquamaris;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<Province> provinciaList;
    String provincias;// Lista de provincias

    // Constructor para recibir la lista de provincias
    public RecyclerViewAdapter(List<Province> provinciaList) {
        this.provinciaList = provinciaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Obtener el objeto Provincia en la posición actual
        Province provincia = provinciaList.get(position);

        // Asignar la imagen y el nombre de la provincia al view holder
        holder.imageView.setImageResource(provincia.getImagen());
        holder.textView.setText(provincia.getNombre());
        holder.btnProv.setText(provincia.getNombre());
        //holder.textView.setOnClickListener(v -> {Intent intent = new Intent (v.getContext(), Consulta.class);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provincias = ((Button)view).getText().toString();
                Intent intent2 = new Intent(view.getContext(), Consulta2.class);
                intent2.putExtra("PROV",provincias);
                view.getContext().startActivity(intent2);
                }
            });
        holder.btnProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provincias = ((Button)view).getText().toString();
                Intent intent3 = new Intent(view.getContext(), Consulta2.class);
                intent3.putExtra("PROV",provincias);
                view.getContext().startActivity(intent3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinciaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button textView, btnProv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagen);
            textView = itemView.findViewById(R.id.texto);
            btnProv = itemView.findViewById(R.id.botonProvincias);

        }
    }

}
