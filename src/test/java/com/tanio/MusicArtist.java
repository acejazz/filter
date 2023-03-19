package com.tanio;

import java.util.Objects;

class MusicArtist {
    String name;
    String genre;
    int numberOfComponents;
    String country;

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumberOfComponents() {
        return numberOfComponents;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicArtist that = (MusicArtist) o;
        return numberOfComponents == that.numberOfComponents && Objects.equals(name, that.name) && Objects.equals(genre, that.genre) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre, numberOfComponents, country);
    }

    @Override
    public String toString() {
        return name;
    }
}
