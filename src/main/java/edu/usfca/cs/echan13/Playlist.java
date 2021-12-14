package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Playlist {
    protected List<Song> songsPlaylist;

    public Playlist() {
        songsPlaylist = new ArrayList<Song>();
    }

    public List<Song> getSongs() {
        return songsPlaylist;
    }

    public void addSong(Song song) {
        songsPlaylist.add(song);
    }

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

    public Playlist mergePlaylists(Playlist otherPlaylist) {
        Playlist mergedPlaylist = new Playlist();
        HashSet<Song> songSet = new HashSet<>();
        songSet.addAll(songsPlaylist);
        songSet.addAll(otherPlaylist.songsPlaylist);
        mergedPlaylist.songsPlaylist.addAll(songSet);

        return mergedPlaylist;
    }

    public void shuffle() {
        Collections.shuffle(songsPlaylist);
    }

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

    /*public Playlist genrePlaylist(String genre) {
        Playlist genrePlaylist = new Playlist();
        for (Song song:songsPlaylist) {
            if (song.getGenre().equals(genre)) {
                genrePlaylist.addSong(song);
            }
        }
        return genrePlaylist;
    }*/

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
