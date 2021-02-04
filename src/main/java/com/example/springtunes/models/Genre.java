package com.example.springtunes.models;

public class Genre {
    private String genreId;
    private String name;

    public Genre(String genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
