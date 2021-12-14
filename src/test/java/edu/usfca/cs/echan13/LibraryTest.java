package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    Library lib;
    Song s1, s2, s3, s4, s5;
    Artist art1, art2;
    Album a1, a2, a3;

    @BeforeEach
    void setUp() {
        lib = new Library();

        s1 = new Song("Starboy");
        s2 = new Song("Starboy");
        s3 = new Song("Starboy");
        s4 = new Song("Escape from LA");
        s5 = new Song("Escape from L.A.");

        art1 = new Artist("Abel");
        art2 = new Artist("The Weeknd");

        a1 = new Album("Starboy");
        a2 = new Album("Starboy (Deluxe)");
        a3 = new Album("After Hours");

        s1.setArtist(art1);
        s1.setAlbum(a1);

        s2.setArtist(art2);
        s2.setAlbum(a1);

        s3.setArtist(art1);
        s3.setAlbum(a2);

        s4.setArtist(art1);
        s4.setAlbum(a3);

        s5.setArtist(art1);
        s5.setAlbum(a3);

        lib.addSong(s1);
        lib.addSong(s2);
        lib.addSong(s3);
        lib.addSong(s4);
        lib.addSong(s5);
    }

    @Test
    void findSong() {
        Song temp = new Song("Starboy");
        temp.setArtist(new Artist("Abel"));
        temp.setAlbum(new Album("Starboy"));

        assertTrue(lib.findSong(temp));
        assertTrue(lib.findSong(s1));
    }

    @Test
    void getSongs() {
        System.out.println(lib.getSongs());
    }

    @Test
    void addSong() {
        Song newSong = new Song("Ordinary Life");
        lib.addSong(newSong);
        assertTrue(lib.getSongs().size() == 6);
    }

    @Test
    void deleteSong() {
        Song temp = new Song("Starboy");
        temp.getArtist().setName("The Weeknd");
        temp.getAlbum().setName("Starboy");

        lib.deleteSong(s5);
        lib.deleteSong(temp);

        assertTrue(lib.getSongs().size() == 3);
    }

    @Test
    void removeDupes() {
        ArrayList<Song> list = lib.getSongs();
        System.out.println("Library:");
        for (Song song:list) {
            System.out.println(song);
        }

        String toRemove = "y\ny\ny";
        System.setIn(new ByteArrayInputStream(toRemove.getBytes()));
        lib.removeDupes();
        System.out.println("\nDupes removed\nNew library:");
        ArrayList<Song> list2 = lib.getSongs();
        for (Song song:list2) {
            System.out.println(song);
        }
    }

    @Test
    void isDup() {
        assertTrue(lib.isDup(s1, s2));
    }

    @Test
    void toXML() {
        System.out.println(lib.toXML());
    }

}