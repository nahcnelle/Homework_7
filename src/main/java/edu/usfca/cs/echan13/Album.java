package edu.usfca.cs.echan13;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  The Album class represents music albums.
 *  It stores values for the album name, artist, and number of songs.
 *  It extends the Entity class.
 *
 *  @author Ellen Chan
 *
 */
public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;
    protected int nSongs;

    /**
     * Creates an empty Album object.
     */
    public Album() {
    }

    /**
     * Creates an Album object using the album title.
     *
     * @param name the album's title
     */
    public Album(String name) {
        super(name);
    }

    /**
     * Returns the songs on the album.
     *
     * @return the list of Song objects on the album
     */
    protected ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * Sets the songs on the album.
     *
     * @param songs an ArrayList of Song objects
     */
    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * Returns the album's artist.
     *
     * @return the Artist object associated with the Album object
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Sets the album's artist.
     *
     * @param artist the Artist object associated with this album
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Retuns the number of songs on the album.
     *
     * @return the number of songs on the album
     */
    public int getNSongs() {
        return nSongs;
    }

    /**
     * Sets the number of songs on the album.
     *
     * @param nSongs the number of songs on the album
     */
    public void setNSongs(int nSongs) {
        this.nSongs = nSongs;
    }

    /**
     * Compares this album to the specified album.
     *
     * @param otherAlbum the other album to compare to
     * @return true if the albums are equivalent, false if not
     */
    public boolean equals(Album otherAlbum) {
        if (this.name.equals(otherAlbum.name)
                || this.entityID == otherAlbum.entityID) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a String object representing the album.
     *
     * @return a String object describing the album
     */
    public String toString() {
        return "Album " + super.toString();
    }

    /**
     * Returns a String object representing the SQL command to add
     * this album to the music.db database and also adds the
     * album to the music.db database.
     *
     * @return a String representing the SQL command to add the album to a database
     */
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

    /**
     * Creates a list from the albums in the music.db database.
     *
     * @return the List object of albums found in the database
     */
    public List<Album> fromSQL() {
        List<Album> tempList = new ArrayList<>();
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
                tempList.add(tempAlbum);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tempList;
    }
}
