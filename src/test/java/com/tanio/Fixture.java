package com.tanio;

class Fixture {
    static MusicArtist beatles() {
        MusicArtist beatles = new MusicArtist();
        beatles.name = "Beatles";
        beatles.genre = "Rock";
        beatles.numberOfComponents = 4;
        beatles.country = "UK - United Kingdom";
        beatles.stillPlaying = false;
        beatles.alive = true;
        return beatles;
    }

    static MusicArtist rollingStones() {
        MusicArtist rollingStones = new MusicArtist();
        rollingStones.name = "Rolling Stones";
        rollingStones.genre = "Rock";
        rollingStones.numberOfComponents = 4;
        rollingStones.country = "UK";
        rollingStones.stillPlaying = true;
        rollingStones.alive = true;
        return rollingStones;
    }

    static MusicArtist madonna() {
        MusicArtist madonna = new MusicArtist();
        madonna.name = "Madonna";
        madonna.genre = "Pop";
        madonna.numberOfComponents = 1;
        madonna.country = "USA";
        madonna.stillPlaying = true;
        madonna.alive = true;
        return madonna;
    }

    static MusicArtist marvinGaye() {
        MusicArtist marvinGaye = new MusicArtist();
        marvinGaye.name = "Marvin Gaye";
        marvinGaye.genre = "R&B";
        marvinGaye.numberOfComponents = 1;
        marvinGaye.country = "USA";
        marvinGaye.stillPlaying = false;
        marvinGaye.alive = false;
        return marvinGaye;
    }

    static MusicArtist bjork() {
        MusicArtist bjork = new MusicArtist();
        bjork.name = "Bjork";
        bjork.genre = "Art Pop";
        bjork.numberOfComponents = 1;
        bjork.country = "Iceland";
        bjork.stillPlaying = true;
        bjork.alive = true;
        return bjork;
    }

    static MusicArtist edithPiaf() {
        MusicArtist edithPiaf = new MusicArtist();
        edithPiaf.name = "Edith Piaf";
        edithPiaf.genre = "Cabaret";
        edithPiaf.numberOfComponents = 1;
        edithPiaf.country = "France";
        edithPiaf.stillPlaying = false;
        edithPiaf.alive = false;
        return edithPiaf;
    }

    static MusicArtist nirvana() {
        MusicArtist nirvana = new MusicArtist();
        nirvana.name = "Nirvana";
        nirvana.genre = "Rock";
        nirvana.numberOfComponents = 3;
        nirvana.country = "USA";
        nirvana.stillPlaying = false;
        nirvana.alive = true;
        return nirvana;
    }

    static MusicArtist bruceSpringsteen() {
        MusicArtist bruceSpringsteen = new MusicArtist();
        bruceSpringsteen.name = "Bruce Springsteen";
        bruceSpringsteen.genre = "Rock";
        bruceSpringsteen.numberOfComponents = 1;
        bruceSpringsteen.country = "USA";
        bruceSpringsteen.stillPlaying = true;
        bruceSpringsteen.alive = true;
        return bruceSpringsteen;
    }

    static MusicArtist eltonJohn() {
        MusicArtist eltonJohn = new MusicArtist();
        eltonJohn.name = "Elton John";
        eltonJohn.genre = "Pop";
        eltonJohn.numberOfComponents = 1;
        eltonJohn.country = "UK";
        eltonJohn.stillPlaying = true;
        eltonJohn.alive = true;
        return eltonJohn;
    }
}