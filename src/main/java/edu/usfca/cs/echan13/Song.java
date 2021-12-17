package edu.usfca.cs.echan13;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  The Song class represents a song.
 *  It stores values for the song's title, artist,
 *  album, and if it is liked.
 *  It extends the Entity class.
 *
 *  @author Ellen Chan
 *
 */
public class Song extends Entity {
    protected Album album;
    protected Artist artist;
    protected boolean liked;

    /**
     * Creates an empty Song object.
     */
    public Song() {
    }

    /**
     * Creates a Song object using the song's title
     *
     * @param name the song's title
     */
    public Song(String name) {
        super(name);
        album = new Album();
        artist = new Artist();
        liked = false;
    }

    /**
     * Returns the album the song is on.
     *
     * @return the song's album
     */
    protected Album getAlbum() {
        return album;
    }

    /**
     * Sets the song's album.
     *
     * @param album the Album associated with this song
     */
    protected void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Returns the song's artist.
     *
     * @return the Artist associated with this song
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Sets the song's artist.
     *
     * @param artist the song's artist
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Returns whether the song is a liked song.
     *
     * @return true if the song is liked, false if not
     */
    public boolean isLiked() {
        return liked;
    }

    /**
     * Set whether the song is a liked song.
     *
     * @param liked the boolean if the song is liked
     */
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    /**
     * Compares this song to the specified song.
     *
     * @param otherSong the other song to compare to
     * @return true if the songs are equivalent, false if not
     */
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

    /**
     * Returns a String object representing the song.
     *
     * @return a String object describing the song
     */
    public String toString() {
        return "Song " + super.toString() + "    " + this.artist + "    " + this.album;
    }

    /**
     * Returns a String object representing the SQL command to add
     * this song to the music.db database and also adds the
     * song to the music.db database.
     *
     * @return a String representing the SQL command to add the song to a database
     */
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

    /**
     * Creates a list from the songs in the music.db database.
     *
     * @return the List object of songs found in the database
     */
    public List<Song> fromSQL() {
        List<Song> tempList = new ArrayList<>();
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
                tempList.add(tempSong);
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
        return tempList;
    }
}
