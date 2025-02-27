package com.proyecto.aquamaris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;



import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

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
        holder.bindData(aData.get(position));
        holder.status.setOnClickListener(new View.OnClickListener() {
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
        Button status;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            city = itemView.findViewById(R.id.cityTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }

        void bindData(final ListarElementos item) {
            // Load the image using Glide
            Glide.with(context)
                    .load(item.getImagen()) // URL of the image to load
                    .into(iconImage); // ImageView where the image will be loaded

            name.setText(item.getName());
            city.setText(item.getCity());
            status.setText(item.getStatus());
        }
    }
}
