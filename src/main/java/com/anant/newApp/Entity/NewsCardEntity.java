package com.anant.newApp.Entity;

import javax.persistence.*;

@Entity
public class NewsCardEntity {


        @GeneratedValue(strategy = GenerationType.AUTO)
        private int Id;

        private String title;

        @Lob
        private String description;
        private String publishedAt;
        private String urlToImage;

        @Id
        private String url;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString(){
        return this.Id+" " + this.description+" " + this.publishedAt+" " + this.url+" " + this.title+" " + this.urlToImage;
    }
}
