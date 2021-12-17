package edu.usfca.cs.echan13;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

/**
 *  The AlbumTest class tests the relevant SQL methods in
 *  the Album class.
 *
 *  @author Ellen Chan
 *
 */
class AlbumTest {
    Album album;
    ResultSet rs;
    Connection connection;

    /**
     * Adds an Album object to a SQL database.
     * Prints the album information for the added
     * Nectar album by Joji.
     */
    @Test
    void toSQL() {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("delete from albums where id = '12'");
            album = new Album("Nectar");
            album.setArtist(new Artist("Joji"));
            album.setNSongs(18);
            album.setEntityID(12);
            album.toSQL();
            rs = statement.executeQuery("select * from albums where id = 12");
            while (rs.next()) {
                System.out.print("album name: " + rs.getString("name"));
                System.out.print(" id: " + rs.getInt("id"));
                System.out.print(" artist: " + rs.getInt("artist"));
                System.out.print(" nSongs: " + rs.getInt("nSongs") + "\n");
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
     * Creates a list of Albums from a database and prints
     * the result.
     */
    @Test
    void fromSQL() {
        album = new Album();
        List<Album> albumList;
        albumList = album.fromSQL();
        for (Album albums:albumList) {
            System.out.println(albums);
        }
    }
}