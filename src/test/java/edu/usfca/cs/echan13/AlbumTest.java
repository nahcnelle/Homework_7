package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7


import org.junit.jupiter.api.Test;

import java.sql.*;

class AlbumTest {
    Album album;
    ResultSet rs;
    Connection connection;

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
            rs = statement.executeQuery("select * from albums");
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

    @Test
    void fromSQL() {
        album = new Album();
        album.fromSQL();
    }
}