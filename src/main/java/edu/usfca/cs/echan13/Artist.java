package edu.usfca.cs.echan13;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  The Artist class represents a musical artist.
 *  It stores values for the artist's name, their number
 *  of songs, and their number of albums.
 *  It extends the Entity class.
 *
 *  @author Ellen Chan
 *
 */
public class Artist extends Entity {


    protected ArrayList<Album> albums;
    protected int nSongs, nAlbums;

    /**
     * Creates an empty Artist object.
     */
    public Artist() {
    }

    /**
     * Creates an Artist object using the artist's name.
     *
     * @param name the artist's name
     */
    public Artist(String name) {
        super(name);
    }

    /**
     * Returns the number of songs the artist has made.
     *
     * @return the number of songs
     */
    public int getNSongs() {
        return nSongs;
    }

    /**
     * Sets the number of songs the artist has made.
     *
     * @param nSongs the number of songs
     */
    public void setNSongs(int nSongs) {
        this.nSongs = nSongs;
    }

    /**
     * Returns the number of albums the artist has made.
     *
     * @return the number of albums
     */
    public int getNAlbums() {
        return nAlbums;
    }

    /**
     * Sets the number of albums the artist has made.
     *
     * @param nAlbums the number of albums
     */
    public void setNAlbums(int nAlbums) {
        this.nAlbums = nAlbums;
    }

    /**
     * Compares this artist to the specified artist.
     *
     * @param otherArtist the other artist to compare to
     * @return true if the artists are equivalent, false if not
     */
    public boolean equals(Artist otherArtist) {
        if (this.name.equals(otherArtist.name)
                || this.entityID == otherArtist.entityID) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a String object representing the artist.
     *
     * @return a String object describing the artist
     */
    public String toString() {
        return "Artist " + super.toString();
    }

    /**
     * Returns a String object representing the SQL command to add
     * this artist to the music.db database and also adds the
     * artist to the music.db database.
     *
     * @return a String representing the SQL command to add the artist to a database
     */
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
     * Creates a list from the artists in the music.db database.
     *
     * @return the List object of artists found in the database
     */
    public List<Artist> fromSQL() {
        List<Artist> tempList = new ArrayList<>();
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
                tempList.add(tempArtist);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tempList;
    }
}
