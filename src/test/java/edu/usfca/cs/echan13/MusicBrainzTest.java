package edu.usfca.cs.echan13;

import org.junit.jupiter.api.Test;

/**
 *  The MusicBrainzTest class tests the relevant methods in
 *  the MusicBrainz class.
 *
 *  @author Ellen Chan
 *
 */
class MusicBrainzTest {

    /**
     * Prints the result for searching for the artist
     * "Beatles" in MusicBrainz.
     */
    @Test
    void artistSearch() {
        System.out.println(MusicBrainz.artistSearch("Beatles"));
    }

    /**
     * Prints the result for searching for the album
     * "After Hours" in MusicBrainz
     */
    @Test
    void albumSearch() {
        System.out.println(MusicBrainz.albumSearch("After Hours"));
    }

    /**
     * Prints the result for searching for the song
     * "Shake It Off" in MusicBrainz.
     */
    @Test
    void songSearch() {
        System.out.println(MusicBrainz.songSearch("Shake It Off"));
    }
}