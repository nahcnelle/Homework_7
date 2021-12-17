package edu.usfca.cs.echan13;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

/**
 *  The ArtistTest class tests the relevant SQL methods in
 *  the Artist class.
 *
 *  @author Ellen Chan
 *
 */
class ArtistTest {
    Artist artist;
    ResultSet rs;
    Connection connection;

    /**
     * Adds an Artist object to a SQL database.
     * Prints the artist information for the added
     * artist Joji.
     */
    @Test
    void toSQL() {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("delete from artists where id = '15'");
            artist = new Artist("Joji");
            artist.setNSongs(46);
            artist.setNAlbums(3);
            artist.setEntityID(15);
            artist.toSQL();
            rs = statement.executeQuery("select * from artists where id = 15");
            while (rs.next()) {
                System.out.print("artist name: " + rs.getString("name"));
                System.out.print(" id: " + rs.getInt("id"));
                System.out.print(" nSongs: " + rs.getInt("nSongs"));
                System.out.print(" nAlbums: " + rs.getInt("nAlbums") + "\n");
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

    /**
     * Creates a list of Artists from a database and prints
     * the result.
     */
    @Test
    void fromSQL() {
        artist = new Artist();
        List<Artist> artistList;
        artistList = artist.fromSQL();
        for (Artist artists:artistList) {
            System.out.println(artists);
        }
    }
}