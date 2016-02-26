package com.twu.biblioteca.model;//Created by SanCoder on 2/26/16.

public class Movie extends LibraryResource {
    private String name;
    private String director;
    private String publishedYear;
    private String rating;

    public Movie(String name, String director, String publishedYear, String rating) {
        this.name = name;
        this.director = director;
        this.publishedYear = publishedYear;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
