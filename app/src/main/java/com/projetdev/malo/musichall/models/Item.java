package com.projetdev.malo.musichall.models;

import java.util.Map;

public abstract class Item {

    private String id;
    private String name;
    private String url;
    private Map<String,String> images;

    Item(String name) {
        this.name = name;
    }

    Item(String id, String name, String url, Map<String,String> images) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.images = images;
    }

    Item(String name, Map<String, String> images) {
        this.name = name;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }
}
