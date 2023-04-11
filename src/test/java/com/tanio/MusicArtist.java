package com.tanio;

class MusicArtist {
    String name;
    String genre;
    int numberOfComponents;
    String country;
    boolean stillPlaying;
    boolean alive;
    String realName;

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

    public boolean getStillPlaying() {
        return stillPlaying;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicArtist that = (MusicArtist) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}