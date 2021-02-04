package com.example.springtunes.models;

public class Artist {
    private String artistId;
    private String name;

    public Artist(String artistId, String name) {
        this.artistId = artistId;
        this.name = name;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
