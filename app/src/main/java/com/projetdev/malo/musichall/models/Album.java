package com.projetdev.malo.musichall.models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class Album extends Item {

    private String id;
    private String name;
    private String url;
    private int year;
    private Artist artist;
    private Map<String,String> images;
    private ArrayList<Track> tracks;
    private ArrayList<String> tags;
    private String summup;
    private String content;

    public Album(String id, String name, String url, Map<String, String> images) {
        super(id, name, url, images);
        this.id = id;
        this.name = name;
        this.url = url;
        this.images = images;
    }

    public Album(String id, String name, String url, Integer year, Artist artist, Map<String, String> images) {
        super(id, name, url, images, year, artist);
        this.id = id;
        this.name = name;
        this.url = url;
        this.year = year;
        this.artist = artist;
        this.images = images;
    }

    public Album(String id, String name, String url, Integer year, Artist artist, Map<String, String> images, ArrayList<Track> tracks, ArrayList<String> tags, String summup, String content) {
        super(id, name, url, images, year, artist);
        this.id = id;
        this.name = name;
        this.url = url;
        this.year = year;
        this.artist = artist;
        this.images = images;
        this.tracks = tracks;
        this.tags = tags;
        this.summup = summup;
        this.content = content;
    }

    @NotNull
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @NotNull
    @Override
    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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

