package com.proyecto.aquamaris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NombrePecesAdapter extends RecyclerView.Adapter<NombrePecesAdapter.PecesViewHolder>
{
    private List<NombrePeces> Data;
    private Context context;

    public NombrePecesAdapter(List<NombrePeces> itemList, Context context)
    {
        this.context = context;
        this.Data = itemList;
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    @NonNull
    @Override
    public PecesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_nombre_peces, null);
        return new PecesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PecesViewHolder holder, int position) {
        NombrePeces nombrePeces = Data.get(position);
        holder.nombres.setText(nombrePeces.getNombrePez());
    }

    public static class PecesViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombres;

        PecesViewHolder(View itemView)
        {
            super(itemView);
            nombres = itemView.findViewById(R.id.nompez);
        }
    }


}
