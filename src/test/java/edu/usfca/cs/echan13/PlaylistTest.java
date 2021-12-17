package edu.usfca.cs.echan13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  The PlaylistTest class tests the relevant methods in
 *  the Playlist class.
 *
 *  @author Ellen Chan
 *
 */
class PlaylistTest {
    Playlist playlist;
    Song s1, s2, s3, s4, s5;
    Artist artist;
    Album a1, a2, a3, a4, a5;

    /**
     * Creates various Song, Artist, and Album objects
     *  for later use in the test methods.
     */
    @BeforeEach
    void setUp() {
        playlist = new Playlist();

        s1 = new Song("Tears in the Rain");
        s2 = new Song("Real Life");
        s3 = new Song("Starboy");
        s4 = new Song("Call Out My Name");
        s5 = new Song("Escape from L.A.");

        artist = new Artist("Abel");

        a1 = new Album("Kiss Land");
        a2 = new Album("Beauty Behind the Madness");
        a3 = new Album("Starboy");
        a4 = new Album("My Dear Melancholy");
        a5 = new Album("After Hours");

        s1.setArtist(artist);
        s1.setAlbum(a1);
        s2.setArtist(artist);
        s2.setAlbum(a2);
        s3.setArtist(artist);
        s3.setAlbum(a3);
        s3.setLiked(true);
        s4.setArtist(artist);
        s4.setAlbum(a4);
        s5.setArtist(artist);
        s5.setAlbum(a5);
        s5.setLiked(true);
    }

    /**
     * Prints the songs in the playlist.
     */
    @Test
    void getSongs() {
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);

        System.out.println("\ngetSongs()");
        System.out.println(playlist.getSongs());
    }

    /**
     * Adds songs to the playlist and checks the size of the playlist.
     */
    @Test
    void addSong() {
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);

        assertTrue(playlist.songsPlaylist.size() == 5);
    }

    /**
     * Adds and deletes songs from the playlist, then checks
     * the playlist size.
     */
    @Test
    void deleteSong() {
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);
        Song temp = new Song("Real Life");
        temp.getArtist().setName("Abel");
        temp.getAlbum().setName("Beauty Behind the Madness");

        playlist.deleteSong(s1);
        playlist.deleteSong(temp);

        assertTrue(playlist.songsPlaylist.size() == 3);
    }

    /**
     * Searches the playlist for songs.
     */
    @Test
    void findSong() {
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);

        Song temp = new Song("Tears in the Rain");
        temp.getArtist().setName("Abel");
        temp.getAlbum().setName("Kiss Land");

        assertTrue(playlist.findSong(s2));
        assertTrue(playlist.findSong(temp));
    }

    /**
     * Merges two playlists together and checks the new
     * playlist's size.
     */
    @Test
    void mergePlaylists() {
        Playlist playlist2 = new Playlist();
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);

        playlist2.addSong(s3);
        playlist2.addSong(s4);
        playlist2.addSong(s5);

        Playlist temp = playlist.mergePlaylists(playlist2);
        assertTrue(temp.songsPlaylist.size() == 5);
        System.out.println("\nMerged Playlists");
        System.out.println(temp.songsPlaylist);
    }

    /**
     * Shuffles the playlist and prints the playlist
     * before and after the shuffling.
     */
    @Test
    void shuffle() {
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);

        System.out.println("\nUnshuffled");
        System.out.println(playlist.songsPlaylist);
        playlist.shuffle();
        System.out.println("Shuffled");
        System.out.println(playlist.songsPlaylist);
    }

    /**
     * Sorts the playlist by liked songs and
     * prints the playlist before and after the
     * sorting.
     */
    @Test
    void sortLiked() {
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);

        System.out.println("\nUnsorted");
        System.out.println(playlist.songsPlaylist);
        playlist.sortLiked();
        System.out.println("Sorted by likes");
        System.out.println(playlist.songsPlaylist);
    }

    /**
     * Prints the XML representation of the library
     */
    @Test
    void toXML() {
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);

        System.out.println(playlist.toXML());
    }
}