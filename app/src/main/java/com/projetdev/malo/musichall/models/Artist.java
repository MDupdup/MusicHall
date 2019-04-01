package com.projetdev.malo.musichall.models;

public class Artist extends Result {

    private int id;
    private String name;
    private String thumbnail;
    private String uri;
    private String description;
    private Disc[] discography;
    private String[] picturesUris;
    private Member[] members;

    public Artist(int id, String name, String thumbnail) {
        super(id, name, thumbnail);
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public Artist(int id, String name, String thumbnail, String uri) {
        super(id, name, thumbnail);
        this.uri = uri;
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public Artist(int id, String name, String uri, String thumbnail, String description, Disc[] discography, String[] picturesUris, Member[] members) {
        super(id, name, thumbnail);
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
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

    public String getThumbnail() {
        return thumbnail;
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
