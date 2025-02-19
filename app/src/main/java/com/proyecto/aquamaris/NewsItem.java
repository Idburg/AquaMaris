package com.proyecto.aquamaris;

public class NewsItem {
    private String title;
    private String imageUrl;
    private String href;

    public NewsItem(String title, String imageUrl,String href) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.href=href;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getHref(){
        return href;
    }
}
