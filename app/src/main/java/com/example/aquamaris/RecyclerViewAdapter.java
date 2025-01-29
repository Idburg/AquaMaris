package com.example.aquamaris;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    /*
    int []arr;


    public RecyclerViewAdapter(int[] arr) {
        this.arr = arr;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(arr[position]);
        holder.textView.setText("Provincia "+position);
    }

    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagen);
            textView = itemView.findViewById(R.id.texto);
        }
    }*/

    private List<Province> provinciaList;  // Lista de provincias

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
    }

    @Override
    public int getItemCount() {
        return provinciaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagen);  // Asegúrate de que el ID esté correcto
            textView = itemView.findViewById(R.id.texto);    // Asegúrate de que el ID esté correcto
        }
    }

}
