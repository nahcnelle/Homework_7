package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import org.junit.jupiter.api.Test;

import java.sql.*;

class SongTest {
    Song song;
    ResultSet rs;
    Connection connection;

    @Test
    void toSQL() {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("delete from songs where id = '11'");
            song = new Song("Call Out My Name");
            song.setAlbum(new Album("My Dear Melancholy"));
            song.setArtist(new Artist("The Weeknd"));
            song.setEntityID(11);
            song.toSQL();
            rs = statement.executeQuery("select * from songs");
            while (rs.next()) {
                System.out.print("song name: " + rs.getString("name"));
                System.out.print(" id: " + rs.getInt("id"));
                System.out.print(" artist: " + rs.getInt("artist"));
                System.out.print(" album: " + rs.getInt("album") + "\n");
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
        song = new Song();
        song.fromSQL();
    }
}