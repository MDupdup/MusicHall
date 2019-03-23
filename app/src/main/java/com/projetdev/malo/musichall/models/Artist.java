package com.projetdev.malo.musichall.models;

public class Artist {

    private int id;
    private String name;
    private String uri;
    private String cover;
    private String description;
    private Disc[] discography;
    private String[] picturesUris;
    private Member[] members;

    public Artist(int id, String name, String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    public Artist(int id, String name, String uri, String cover) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.cover = cover;
    }

    public Artist(int id, String name, String uri, String description, Disc[] discography, String[] picturesUris, Member[] members) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.description = description;
        this.discography = discography;
        this.picturesUris = picturesUris;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    public Disc[] getDiscography() {
        return discography;
    }

    public String[] getPicturesUris() {
        return picturesUris;
    }

    public Member[] getMembers() {
        return members;
    }
}
