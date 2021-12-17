package edu.usfca.cs.echan13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *  The Playlist class represents a music playlist.
 *  It stores a list of Song objects which can be added
 *  and deleted to, searched through, and shuffled.
 *
 *  @author Ellen Chan
 *
 */
public class Playlist {
    protected List<Song> songsPlaylist;

    /**
     * Creates a Playlist with an empty list of Songs.
     */
    public Playlist() {
        songsPlaylist = new ArrayList<Song>();
    }

    /**
     * Returns a list of the songs in the playlist
     *
     * @return a List of the Songs
     */
    public List<Song> getSongs() {
        return songsPlaylist;
    }

    /**
     * Adds a Song object to the playlist.
     *
     * @param song the Song object to be added
     */
    public void addSong(Song song) {
        songsPlaylist.add(song);
    }

    /**
     * Deletes a Song object from the playlist.
     *
     * @param song the Song object to be deleted
     * @return true o=if the Song is removed, false if not
     */
    public boolean deleteSong(Song song) {
        boolean deleted = false;
        if (songsPlaylist.remove(song)) {
            deleted = true;
        } else {
            int i = 0;
            while (!deleted && i < songsPlaylist.size()) {
                if (songsPlaylist.get(i).getName().equals(song.getName())
                        && songsPlaylist.get(i).getArtist().getName().equals(song.getArtist().getName())
                        && songsPlaylist.get(i).getAlbum().getName().equals(song.getAlbum().getName())) {
                    songsPlaylist.remove(i);
                    deleted = true;
                }
                i++;
            }
        }
        return deleted;
    }

    /**
     * Searches the playlist of songs for a specific song.
     *
     * @param song the Song object to find
     * @return true if the Song is in the playlist, false if not
     */
    public boolean findSong(Song song) {
        boolean found = false;
        if (songsPlaylist.contains(song)) {
            found = true;
        } else {
            int i = 0;
            while (!found && i < songsPlaylist.size()) {
                if (songsPlaylist.get(i).getName().equals(song.getName())
                        && songsPlaylist.get(i).getArtist().getName().equals(song.getArtist().getName())
                        && songsPlaylist.get(i).getAlbum().getName().equals(song.getAlbum().getName())) {
                    found = true;
                }
                i++;
            }
        }
        return found;
    }

    /**
     * Creates a new Playlist by combining the songs in this playlist
     * with another.
     *
     * @param otherPlaylist the other playlist to add to this one
     * @return a new Playlist object containing the songs in both playlists
     */
    public Playlist mergePlaylists(Playlist otherPlaylist) {
        Playlist mergedPlaylist = new Playlist();
        HashSet<Song> songSet = new HashSet<>();
        songSet.addAll(songsPlaylist);
        songSet.addAll(otherPlaylist.songsPlaylist);
        mergedPlaylist.songsPlaylist.addAll(songSet);

        return mergedPlaylist;
    }

    /**
     * Shuffles the order of the songs in this playlist
     */
    public void shuffle() {
        Collections.shuffle(songsPlaylist);
    }

    /**
     * Rearranges so liked songs are at the front of the list
     */
    public void sortLiked(){
        int numLiked = 0;
        for (int i = 0; i < songsPlaylist.size(); i++) {
            if (songsPlaylist.get(i).isLiked()) {
                numLiked++;
                Song temp = songsPlaylist.get(numLiked - 1);
                songsPlaylist.set(numLiked - 1, songsPlaylist.get(i));
                songsPlaylist.set(i, temp);
            }
        }
    }

    /**
     * Returns a String object representing the Songs in this
     * Playlist as XML.
     *
     * @return the String representation of the XML
     */
    public String toXML() {
        StringBuilder buf = new StringBuilder();
        buf.append("<library>\n  <songs>\n");
        for (Song song:songsPlaylist) {
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
