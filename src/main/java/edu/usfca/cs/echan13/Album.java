package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import java.sql.*;
import java.util.ArrayList;

public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;
    protected int nSongs;

    public Album() {
    }

    public Album(String name) {
        super(name);
    }

    public String getName() {
        return name;
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getNSongs() {
        return nSongs;
    }

    public void setNSongs(int nSongs) {
        this.nSongs = nSongs;
    }

    public boolean equals(Album otherAlbum) {
        if (this.name.equals(otherAlbum.name)
                || this.entityID == otherAlbum.entityID) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Album " + super.toString();
    }

    public String toSQL() {
        StringBuilder buf = new StringBuilder();
        buf.append("insert into albums values(");
        buf.append(this.entityID);
        buf.append(", \'");
        buf.append(this.name);
        buf.append("\', ");
        buf.append(this.artist.entityID);
        buf.append(", ");
        buf.append(this.nSongs);
        buf.append(")");

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
            ResultSet rsAlbum = statement.executeQuery("select * from albums");

            while (rsAlbum.next()) {
                Artist tempArtist = new Artist();
                Album tempAlbum = new Album();
                ResultSet rsArtist;
                tempAlbum.setEntityID(rsAlbum.getInt("id"));
                tempAlbum.name = rsAlbum.getString("name");

                int artID = rsAlbum.getInt("artist");
                tempArtist.setEntityID(artID);
                StringBuilder artBuf = new StringBuilder("select * from artists where id = ");
                artBuf.append(artID);
                statement = connection.createStatement();
                rsArtist = statement.executeQuery(artBuf.toString());
                while (rsArtist.next()) {
                    tempArtist.setName(rsArtist.getString("name"));
                }
                tempAlbum.setArtist(tempArtist);

                tempAlbum.nSongs = rsAlbum.getInt("nSongs");
                System.out.println(tempAlbum);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
