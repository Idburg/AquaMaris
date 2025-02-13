package com.proyecto.aquamaris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NombrePecesAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    private List<NombrePeces> Data;
    private LayoutInflater inflater;
    private Context context;

    public NombrePecesAdapter(List<NombrePeces> itemList, Context context)
    {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.Data = itemList;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
