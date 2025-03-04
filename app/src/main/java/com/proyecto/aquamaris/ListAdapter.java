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



import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.N;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    private List<ListarElementos> aData = new List<ListarElementos>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<ListarElementos> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(ListarElementos listarElementos) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends ListarElementos> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends ListarElementos> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public ListarElementos get(int index) {
            return null;
        }

        @Override
        public ListarElementos set(int index, ListarElementos element) {
            return null;
        }

        @Override
        public void add(int index, ListarElementos element) {

        }

        @Override
        public ListarElementos remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<ListarElementos> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<ListarElementos> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<ListarElementos> subList(int fromIndex, int toIndex) {
            return Collections.emptyList();
        }
    };
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
