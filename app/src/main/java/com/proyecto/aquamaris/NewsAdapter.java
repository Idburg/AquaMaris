package com.proyecto.aquamaris;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<NewsItem> newsList;
    private final Context context;

    public NewsAdapter(List<NewsItem> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);
        holder.title.setText(newsItem.getTitle());

        // Cargar la imagen con Glide
        if (!newsItem.getImageUrl().isEmpty()) {
            Glide.with(context).load(newsItem.getImageUrl()).into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.pez); // Imagen por defecto
        }

        // Cuando el item de noticias es clickeado, abrir el WebView con la URL
        holder.itemView.setOnClickListener(v -> {
            // Obtener el href de la noticia
            String href = newsItem.getHref(); // Asegúrate de tener un getter en NewsItem para href

            // Crear el Intent para abrir la actividad WebNews
            Intent intent = new Intent(context, WebNews.class);
            intent.putExtra("url", href); // Pasar el href a la actividad WebNews
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitle);
            image = itemView.findViewById(R.id.newsImage);
        }
    }
}