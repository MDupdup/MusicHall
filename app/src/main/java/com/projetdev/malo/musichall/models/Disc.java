package com.projetdev.malo.musichall.models;

public class Disc {

    private int id;
    private String name;
    private int dateReleased;
    private String thumbnail;
    private String label;

    public Disc(int id, String name, int dateReleased, String thumbnail, String label) {
        this.id = id;
        this.name = name;
        this.dateReleased = dateReleased;
        this.thumbnail = thumbnail;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDateReleased() {
        return dateReleased;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getLabel() {
        return label;
    }
}
