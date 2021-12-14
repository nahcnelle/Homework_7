package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import java.sql.*;
import java.util.ArrayList;

public class Artist extends Entity {

    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;
    protected int nSongs, nAlbums;

    public Artist() {
    }

    public Artist(String name) {
        super(name);
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public int getNSongs() {
        return nSongs;
    }

    public void setNSongs(int nSongs) {
        this.nSongs = nSongs;
    }

    public int getNAlbums() {
        return nAlbums;
    }

    public void setNAlbums(int nAlbums) {
        this.nAlbums = nAlbums;
    }

    public boolean equals(Artist otherArtist) {
        if (this.name.equals(otherArtist.name)
                || this.entityID == otherArtist.entityID) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Artist " + super.toString();
    }

    public String toSQL() {
        StringBuilder buf = new StringBuilder();
        buf.append("insert into artists values(");
        buf.append(this.entityID);
        buf.append(", \'");
        buf.append(this.name);
        buf.append("\', ");
        buf.append(this.nSongs);
        buf.append(", ");
        buf.append(this.nAlbums);
        buf.append(");");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(buf.toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return buf.toString();
    }

    public void fromSQL() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rsArtist = statement.executeQuery("select * from artists");

            while (rsArtist.next()) {
                Artist tempArtist = new Artist();
                tempArtist.setEntityID(rsArtist.getInt("id"));
                tempArtist.name = rsArtist.getString("name");
                tempArtist.nSongs = rsArtist.getInt("nSongs");
                tempArtist.nAlbums = rsArtist.getInt("nAlbums");
                System.out.println(tempArtist);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
