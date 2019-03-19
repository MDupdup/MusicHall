package com.projetdev.malo.musichall.models;

public class Member {

    private int id;
    private String name;
    private String url;
    private boolean isActive;

    public Member(int id, String name, String url, boolean isActive) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isActive() {
        return isActive;
    }
}