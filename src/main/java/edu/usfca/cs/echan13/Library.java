package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Song> songs;

    public Library() {
        songs = new ArrayList<Song>();
    }

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

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

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

    protected boolean isDup(Song s1, Song s2) {
        if (s1.equals(s2)) {
            return true;
        } else {
            return false;
        }
    }

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
