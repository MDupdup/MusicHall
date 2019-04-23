package com.projetdev.malo.musichall.models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Artist extends Result {

    private int id;
    private String name;
    private String thumbnail;
    private String uri;
    private String description;
    private ArrayList<Disc> discography;
    private ArrayList<Member> members;

    public Artist(String name) {
        super(0, name, "", "", "", null);
        this.name = name;
    }

    public Artist(int id, String name, String thumbnail) {
        super(id, name, thumbnail, "", "", null);
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public Artist(int id, String name, String thumbnail, String uri) {
        super(id, name, thumbnail, "", "", null);
        this.uri = uri;
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public Artist(int id, String name, String uri, String thumbnail, String description, ArrayList<Disc> discography, ArrayList<Member> members) {
        super(id, name, thumbnail, "", "", null);
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.uri = uri;
        this.description = description;
        this.discography = discography;
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

    public ArrayList<Disc> getDiscography() {
        return discography;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }
}
