package com.proyecto.aquamaris;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListarElementos> aData;
    private LayoutInflater mInflater;
    private Context context;
    String nombrepez;

    public ListAdapter(List<ListarElementos> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.aData = itemList;
    }

    @Override
    public int getItemCount() {
        return aData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.elementos, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        ListarElementos le = aData.get(position);
        //holder.name.setText(le.getName());
        holder.bindData(aData.get(position));

        // Set the click listener on the CardView
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombrepez = le.getName();
                Intent intent = new Intent(view.getContext(), PezIndividual.class);
                intent.putExtra("PEZ", nombrepez);
                view.getContext().startActivity(intent);
            }
        });
    }

    public void setItems(List<ListarElementos> items) {
        aData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, city;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            city = itemView.findViewById(R.id.cityTextView);
            cardView = itemView.findViewById(R.id.cv); // Reference to the CardView
        }

        void bindData(final ListarElementos item) {
            // Cargar la imagen usando Glide
            if (item.getImagen() instanceof String) {
                // Si es una URL, cargar la imagen desde la URL
                String imagenUrl = (String) item.getImagen();
                Glide.with(context)
                        .load(imagenUrl)
                        .error(R.drawable.noimage) // Imagen predeterminada si falla la carga
                        .into(iconImage);
            } else if (item.getImagen() instanceof Integer) {
                // Si es un recurso drawable, cargar directamente
                int imagenResId = (Integer) item.getImagen();
                Glide.with(context)
                        .load(imagenResId)
                        .into(iconImage);
            }

            name.setText(item.getName());
            city.setText(item.getCity());
        }
    }
}