package com.proyecto.aquamaris;

public class NewsItem {
    private String title;
    private String imageUrl;

    public NewsItem(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
