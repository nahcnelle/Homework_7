package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7


import org.junit.jupiter.api.Test;

import java.sql.*;


class ArtistTest {
    Artist artist;
    ResultSet rs;
    Connection connection;

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
            rs = statement.executeQuery("select * from artists");
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

    @Test
    void fromSQL() {
        artist = new Artist();
        artist.fromSQL();
    }
}