package com.projetdev.malo.musichall.models;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Artist extends Item {

    private String id;
    private String name;
    private String url;
    private Map<String,String> images;
    private String playCount;
    private Boolean isOnTour;
    private ArrayList<Artist> similar;
    private String summup;
    private String content;


    public Artist(String name) {
        super(name, new HashMap<>());
        this.name = name;

    }

    public Artist(String name, Map<String,String> images) {
        super(name, images);
        this.name = name;
        this.images = images;
    }

    public Artist(String id, String name, String url, String playCount, Map<String, String> images) {
        super(id, name, url, images);
        this.playCount = playCount;
    }

    public Artist(String id, String name, String url, Map<String, String> images, String playCount, Boolean isOnTour, ArrayList<Artist> similar, String summup, String content) {
        super(id, name, url, images);
        this.id = id;
        this.name = name;
        this.url = url;
        this.images = images;
        this.playCount = playCount;
        this.isOnTour = isOnTour;
        this.similar = similar;
        this.summup = summup;
        this.content = content;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Map<String, String> getImages() {
        return images;
    }

    @Override
    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
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
}
