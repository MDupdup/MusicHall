package com.projetdev.malo.musichall.models;

import java.util.ArrayList;
import java.util.Map;

public class Album extends Item {

    private ArrayList<Track> tracks;
    private ArrayList<String> tags;
    private String summup;
    private String content;

    public Album(String id, String name, String url, Map<String, String> images) {
        super(id, name, url, images);
    }

    public Album(String id, String name, String url, Integer year, Artist artist, Map<String, String> images) {
        super(id, name, url, images, year, artist);
    }

    public Album(String id, String name, String url, Integer year, Artist artist, Map<String, String> images, ArrayList<Track> tracks, ArrayList<String> tags, String summup, String content) {
        super(id, name, url, images, year, artist);
        this.tracks = tracks;
        this.tags = tags;
        this.summup = summup;
        this.content = content;
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

