package edu.usfca.cs.echan13;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *  The Library class represents a music library.
 *  It stores a list of Song objects.
 *
 *  @author Ellen Chan
 *
 */
public class Library {
    private ArrayList<Song> songs;

    /**
     * Creates a Library with an empty list of Songs.
     */
    public Library() {
        songs = new ArrayList<Song>();
    }

    /**
     * Searches the library for a specific song.
     *
     * @param song the song to look for
     * @return true if the Song object is in the library, false if not
     */
    public boolean findSong(Song song) {
        boolean found = false;
        if (songs.contains(song)) {
            found = true;
        } else {
            int i = 0;
            while (!found && i < songs.size()) {
                if (songs.get(i).getName().equals(song.getName())
                        && songs.get(i).getArtist().getName().equals(song.getArtist().getName())
                        && songs.get(i).getAlbum().getName().equals(song.getAlbum().getName())) {
                    found = true;
                }
                i++;
            }
        }
        return found;
    }

    /**
     * Returns a list of the songs in the library
     *
     * @return an ArrayList of the present Song objects
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * Adds a song to the library.
     *
     * @param song the Song object to add
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Deletes a song from the library
     *
     * @param song the Song object to delete
     * @return true if the Song object is deleted, false if not
     */
    public boolean deleteSong(Song song) {
        boolean deleted = false;
        if (songs.remove(song)) {
            deleted = true;
        } else {
            int i = 0;
            while (!deleted && i < songs.size()) {
                if (songs.get(i).getName().equals(song.getName())
                        && songs.get(i).getArtist().getName().equals(song.getArtist().getName())
                        && songs.get(i).getAlbum().getName().equals(song.getAlbum().getName())) {
                    songs.remove(i);
                    deleted = true;
                }
                i++;
            }
        }
        return deleted;
    }

    /**
     * Searches the library and removes any duplicates.
     */
    public void removeDupes() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < songs.size(); i++) {
            for (int j = i+1; j < songs.size(); j++) {
                if (isDup(songs.get(i), songs.get(j))) {
                    System.out.println("Duplicate found.\n\t" +
                            songs.get(i) + "\n\t" + songs.get(j) +
                            "\nWould you like to remove the duplicate? (y/n)");
                    String response = scanner.nextLine();
                    if (response.equals("y")) {
                        songs.remove(j);
                        j--;
                    }
                }
            }
        }
    }

    /**
     * Compares two songs to determine if they are duplicates.
     *
     * @param s1 the first Song object to compare
     * @param s2 the other Song object to compare
     * @return true if the songs are duplicates, false if not
     */
    protected boolean isDup(Song s1, Song s2) {
        if (s1.equals(s2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a String object representing the Songs in this
     * Library as XML.
     *
     * @return the String representation of the XML
     */
    public String toXML() {
        StringBuilder buf = new StringBuilder();
        buf.append("<library>\n  <songs>\n");
        for (Song song:songs) {
            buf.append("\t<song id=\"" + song.getEntityID()+ "\">\n");
            buf.append("\t\t<title>\n");
            buf.append("\t\t\t" + song.getName() + "\n");
            buf.append("\t\t</title>\n");
            buf.append("\t\t<artist id=\"" + song.getArtist().getEntityID() + "\">\n");
            buf.append("\t\t\t" + song.getArtist().getName() + "\n");
            buf.append("\t\t</artist>\n");
            buf.append("\t\t<album id=\"" + song.getAlbum().getEntityID() + "\">\n");
            buf.append("\t\t\t" + song.getAlbum().getName() + "\n");
            buf.append("\t\t</album>\n");
            buf.append("\t</song>\n");
        }
        buf.append("  </songs>\n</library>");
        return buf.toString();
    }

}
