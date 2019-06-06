package com.projetdev.malo.musichall.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Artist extends Item {

    private Boolean isOnTour;
    private ArrayList<Artist> similar;
    private String summup;
    private String content;
    private ArrayList<Album> albums;
    private ArrayList<String> tags;


    public Artist(String name) {
        super(name, new HashMap<>());
    }

    public Artist(String name, Map<String,String> images) {
        super(name, images);
    }

    public Artist(String id, String name, String url, String playCount, Map<String, String> images) {
        super(id, name, url, images, playCount);
    }

    public Artist(String id, String name, String url, Map<String, String> images, String playCount, Boolean isOnTour, ArrayList<Artist> similar, String summup, String content, ArrayList<Album> albums, ArrayList<String> tags) {
        super(id, name, url, images, playCount);
        this.isOnTour = isOnTour;
        this.similar = similar;
        this.summup = summup;
        this.content = content;
        this.albums = albums;
        this.tags = tags;
    }

    public Boolean getOnTour() {
        return isOnTour;
    }

    public void setOnTour(Boolean onTour) {
        isOnTour = onTour;
    }

    public ArrayList<Artist> getSimilar() {
        return similar;
    }

    public void setSimilar(ArrayList<Artist> similar) {
        this.similar = similar;
    }

    public String getSummup() {
        return summup;
    }

    public void setSummup(String summup) {
        this.summup = summup;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
