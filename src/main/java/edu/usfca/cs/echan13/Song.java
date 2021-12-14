package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import java.sql.*;

public class Song extends Entity {
    protected Album album;
    protected Artist artist;
    protected boolean liked;

    public Song() {
    }

    public Song(String name) {
        super(name);
        album = new Album();
        artist = new Artist();
        liked = false;
    }

    protected Album getAlbum() {
        return album;
    }

    protected void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean equals(Song otherSong) {
        String tempSong = this.name.toLowerCase().replaceAll("\\p{Punct}", "");
        String tempOtherSong = otherSong.name.toLowerCase().replaceAll("\\p{Punct}", "");

        if (this.entityID == otherSong.entityID) {
            return true;
        } else if (this.name.equals(otherSong.name)
                && (this.album.equals(otherSong.getAlbum())
                || this.artist.equals(otherSong.getArtist()))) {
            return true;
        } else if (tempSong.equals(tempOtherSong)
                && (this.album.equals(otherSong.getAlbum())
                && this.artist.equals(otherSong.getArtist()))) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Song " + super.toString() + "    " + this.artist + "    " + this.album;
    }

    public String toSQL() {
        StringBuilder buf = new StringBuilder();
        buf.append("insert into songs values(");
        buf.append(this.entityID);
        buf.append(", \'");
        buf.append(this.name);
        buf.append("\', ");
        buf.append(this.artist.entityID);
        buf.append(", ");
        buf.append(this.album.entityID);
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
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rsSong = statement.executeQuery("select * from songs");

            while (rsSong.next()) {
                Song tempSong = new Song();
                Artist tempArtist = new Artist();
                Album tempAlbum = new Album();
                ResultSet rsAlbum, rsArtist;

                tempSong.setEntityID(rsSong.getInt("id"));
                tempSong.name = rsSong.getString("name");

                int artID = rsSong.getInt("artist");
                tempArtist.setEntityID(artID);
                StringBuilder artBuf = new StringBuilder("select * from artists where id = ");
                artBuf.append(artID);
                statement = connection.createStatement();
                rsArtist = statement.executeQuery(artBuf.toString());
                while (rsArtist.next()) {
                    tempArtist.setName(rsArtist.getString("name"));
                }
                tempSong.setArtist(tempArtist);

                int albID = rsSong.getInt("album");
                tempAlbum.setEntityID(albID);
                StringBuilder albBuf = new StringBuilder("select * from albums where id = ");
                albBuf.append(albID);
                statement = connection.createStatement();
                rsAlbum = statement.executeQuery(albBuf.toString());
                while (rsAlbum.next()) {
                    tempAlbum.setName(rsAlbum.getString("name"));
                }
                tempSong.setAlbum(tempAlbum);
                System.out.println(tempSong);
            }
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
    }
}
