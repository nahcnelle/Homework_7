package edu.usfca.cs.echan13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  The LibraryTest class tests the relevant methods in
 *  the Library class.
 *
 *  @author Ellen Chan
 *
 */
class LibraryTest {
    Library lib;
    Song s1, s2, s3, s4, s5;
    Artist art1, art2;
    Album a1, a2, a3;

    /**
     * Creates various Song, Artist, and Album objects
     *  and adds them to a library for later use in
     *  the test methods.
     */
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

    /**
     * Looks for a song in the library.
     */
    @Test
    void findSong() {
        Song temp = new Song("Starboy");
        temp.setArtist(new Artist("Abel"));
        temp.setAlbum(new Album("Starboy"));

        assertTrue(lib.findSong(temp));
        assertTrue(lib.findSong(s1));
    }

    /**
     * Prints the songs in the library.
     */
    @Test
    void getSongs() {
        System.out.println(lib.getSongs());
    }

    /**
     * Adds a song to the library and checks the
     * new size of the library.
     */
    @Test
    void addSong() {
        Song newSong = new Song("Ordinary Life");
        lib.addSong(newSong);
        assertTrue(lib.getSongs().size() == 6);
    }

    /**
     * Deletes a song from the library and checks the
     * new size of the library
     */
    @Test
    void deleteSong() {
        Song temp = new Song("Starboy");
        temp.getArtist().setName("The Weeknd");
        temp.getAlbum().setName("Starboy");

        lib.deleteSong(s5);
        lib.deleteSong(temp);

        assertTrue(lib.getSongs().size() == 3);
    }

    /**
     * Removes duplicate songs from the library.
     * Prints the library before and after the removal.
     */
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

    /**
     * Checks if two songs in a library are duplicates.
     */
    @Test
    void isDup() {
        assertTrue(lib.isDup(s1, s2));
    }

    /**
     * Prints the XML representation of the library
     */
    @Test
    void toXML() {
        System.out.println(lib.toXML());
    }

}