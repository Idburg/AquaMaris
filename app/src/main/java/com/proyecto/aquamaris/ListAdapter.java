package com.proyecto.aquamaris;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aquamaris.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    private List<ListarElementos> aData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListarElementos> itemList, Context context)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.aData = itemList;
    }

    @Override
    public int getItemCount()
    {
        return aData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.elementos, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
    {
        holder.bindData(aData.get(position));
    }

    public void setItems(List<ListarElementos> items)
    {
        aData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iconImage;
        TextView name, city, status;

        ViewHolder(View itemView)
        {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            city = itemView.findViewById(R.id.cityTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }

        void bindData(final ListarElementos item)
        {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            city.setText(item.getCity());
            status.setText(item.getStatus());
        }
    }
}
