package edu.usfca.cs.echan13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *  The UserInterface class implements an application in which a user
 *  can interact with a music library. Menu options such as the
 *  addition or deletion of songs to the library  are displayed on
 *  the console for the user to select.
 *
 *  @author Ellen Chan
 *
 */
public class UserInterface {

    public static void main(String[] args) throws FileNotFoundException {
        Library lib = new Library();
        Song tempSong;
        Playlist tempPlaylist;
        Map<String, Playlist> playlistMap = new HashMap<>();
        Map<String, Song> songMap = new HashMap<>();
        Map<String, Artist> artistMap = new HashMap<>();
        Map<String, Album> albumMap = new HashMap<>();
        Scanner userInterfaceScanner = new Scanner(System.in);
        String playlistName;
        String selectOptionAgain;

        // First, adding the songs, artists, and albums from the music.db database to the library
        System.out.println("Adding songs, artists, and albums to library from music.db");
        loadSongFromSQL(lib, songMap);
        loadArtistFromSQL(artistMap);
        loadAlbumFromSQL(albumMap);

        // Displays user options
        System.out.println("-Music Library-");
        do {
            // prints the menu of options to the console
            System.out.println("1. Add a song to the library\n" +
                    "2. Delete a song form the library\n" +
                    "3. Add an artist to the library\n" +
                    "4. Add an album to the library\n" +
                    "5. Display all songs in the library\n" +
                    "6. Generate XML file from song in the library\n" +
                    "7. Add a song to a playlist\n" +
                    "8. Delete a song from a playlist\n" +
                    "9. Create a playlist from a specific artist\n" +
                    "10. Display all songs in a playlist\n" +
                    "11. Generate XML from a playlist\n");
            System.out.print("-Select a number from the options above: ");

            // executes menu selection based on user input
            switch (userInterfaceScanner.nextInt()) {

                // To add a song to the library and the database
                case 1:
                    System.out.println("Song to add to library:");
                    System.out.print("Do you know all of the song info (such as song title, artist name, and album)? (y/n) ");
                    userInterfaceScanner.nextLine();
                    if (!userInterfaceScanner.nextLine().equals("y")){
                        System.out.print("Enter the song title: ");
                        String tempData = userInterfaceScanner.nextLine();

                        Map<String, String> tempSongMB = MusicBrainz.songSearch(tempData);
                        if (songMap.containsKey(tempSongMB.get("songName"))) {
                            Song tempMBSong = songMap.get("songName");
                            if (tempMBSong.getArtist().equals(tempSongMB.get("artistName")) || tempMBSong.getAlbum().equals(tempSongMB.get("albumName"))) {
                                System.out.println("Song is already in library");
                                break;
                            }
                        }
                        if (!tempSongMB.get("songName").equals("")) {
                            Song temp = new Song(tempSongMB.get("songName"));
                            if (artistMap.containsKey(tempSongMB.get("artistName"))) {
                                temp.setArtist(artistMap.get(tempSongMB.get("artistName")));
                            } else {
                                Artist songArtist = new Artist(tempSongMB.get("artistName"));
                                temp.setArtist(songArtist);
                                artistMap.put(tempSongMB.get("artistName").toLowerCase(), songArtist);
                                songArtist.toSQL();
                            }
                            if (albumMap.containsKey(tempSongMB.get("albumName"))) {
                                temp.setAlbum(albumMap.get(tempSongMB.get("albumName")));
                            } else {
                                Album songAlbum = new Album(tempSongMB.get("albumName"));
                                temp.setAlbum(songAlbum);
                                albumMap.put(tempSongMB.get("albumName").toLowerCase(), songAlbum);
                                songAlbum.toSQL();
                            }
                            lib.addSong(temp);
                            songMap.put(tempSongMB.get("songName").toLowerCase(), temp);
                            temp.toSQL();
                            System.out.println(temp);
                            System.out.println("Song added to library.");
                        }
                        break;
                    }
                    tempSong = userSong(songMap, artistMap, albumMap);
                    if (!songMap.containsKey(tempSong.name.toLowerCase())) {
                        lib.addSong(tempSong);
                        songMap.put(tempSong.name.toLowerCase(), tempSong);
                        tempSong.toSQL();
                        System.out.println("Song added to library.");
                    } else {
                        System.out.println("Song already in library");
                    }
                    break;

                // To delete a song from the library and the database
                case 2:
                    userInterfaceScanner.nextLine();
                    System.out.println("Song to delete from library:");
                    System.out.print("Enter the song's title: ");
                    String songName = userInterfaceScanner.nextLine();
                    if (songMap.containsKey(songName.toLowerCase())) {
                        Song songToDelete = songMap.get(songName.toLowerCase());
                        if (lib.deleteSong(songToDelete)) {
                            songMap.remove(songToDelete);
                            System.out.println("Song deleted.");
                            System.out.println(songToDelete);

                            Connection connection = null;
                            try {
                                StringBuilder buf = new StringBuilder();
                                connection = DriverManager.getConnection("jdbc:sqlite:music.db");
                                Statement statement = connection.createStatement();
                                statement.setQueryTimeout(30);
                                buf.append("delete from songs where name = ");
                                buf.append(songToDelete);
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
                        } else {
                            System.out.println("Song cannot be deleted");
                        }
                    } else {
                        System.out.println("Song not found in library.");
                    }
                    break;

                // To add an artist to the library and database
                case 3:
                    Map<String, String> artistMB;
                    userInterfaceScanner.nextLine();
                    System.out.println("Artist to add to library:");
                    System.out.print("Enter the artist's name: ");
                    String artistName = userInterfaceScanner.nextLine();
                    if (artistMap.containsKey(artistName.toLowerCase())) {
                        System.out.println("Artist already in library");
                        break;
                    } else {
                        artistMB = MusicBrainz.artistSearch(artistName);
                    }
                    if (!artistMB.get("artistName").equals("")) {
                        Artist tempArtist = new Artist(artistMB.get("artistName"));
                        artistMap.put(artistMB.get("artistName").toLowerCase(), tempArtist);
                        System.out.println("Artist added to library");
                        System.out.println(tempArtist);
                    }
                    break;

                // To add an album to the library and the database
                case 4:
                    Map<String, String> albumMB;
                    userInterfaceScanner.nextLine();
                    System.out.println("Album to add to library:");
                    System.out.print("Enter the album name: ");
                    String albumName = userInterfaceScanner.nextLine();
                    if (albumMap.containsKey(albumName.toLowerCase())) {
                        System.out.println("Album already in library");
                        break;
                    } else {
                        albumMB = MusicBrainz.albumSearch(albumName);
                        if (!albumMB.get("albumName").equals("")) {
                            Album tempAlbum = new Album(albumMB.get("albumName"));
                            if (artistMap.containsKey(albumMB.get("artistName"))) {
                                tempAlbum.setArtist(artistMap.get(albumMB.get("artistName")));
                            } else {
                                tempAlbum.setArtist(new Artist(albumMB.get("artistName")));
                            }
                            albumMap.put(albumMB.get("albumName").toLowerCase(), tempAlbum);
                            System.out.println("Album added to library");
                            System.out.print(tempAlbum);
                            System.out.println(tempAlbum.getArtist());
                        }
                    }
                    break;

                // Displays all songs in the library to the console
                case 5:
                    System.out.println("Displaying songs in library:");
                    for (Song song:lib.getSongs()) {
                        System.out.println(song);
                    }
                    userInterfaceScanner.nextLine();
                    break;

                // Creates an XML file from the songs in the library
                case 6:
                    System.out.println("Generating XML file from library");
                    File file = new File("library.xml");
                    PrintWriter outputFile = new PrintWriter(file);
                    outputFile.println(lib.toXML());
                    outputFile.close();
                    userInterfaceScanner.nextLine();
                    break;

                // To add a song to a playlist
                case 7:
                    System.out.println("Add song to a playlist:");
                    playlistName = whichPlaylist();
                    userInterfaceScanner.nextLine();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.print("Playlist does not exist. Would you like create a new playlist and add a song? (y/n) ");
                        if (userInterfaceScanner.nextLine().equals("y")) {

                            tempSong = userSong(songMap, artistMap, albumMap);
                            tempPlaylist = new Playlist();
                            tempPlaylist.addSong(tempSong);
                            playlistMap.put(playlistName, tempPlaylist);
                            System.out.println("Song added to playlist.");

                            if (!songMap.containsKey(tempSong.name.toLowerCase())) {
                                lib.addSong(tempSong);
                                songMap.put(tempSong.name.toLowerCase(), tempSong);
                                tempSong.toSQL();
                            }
                            break;
                        }
                        break;
                    }
                    tempSong = userSong(songMap, artistMap, albumMap);
                    playlistMap.get(playlistName).addSong(tempSong);
                    System.out.println("Song added to playlist.");

                    if (!songMap.containsKey(tempSong.name.toLowerCase())) {
                        lib.addSong(tempSong);
                        songMap.put(tempSong.name.toLowerCase(), tempSong);
                        tempSong.toSQL();
                    }
                    break;

                // To delete a song from a playlist
                case 8:
                    System.out.println("Delete a song from a playlist:");
                    playlistName = whichPlaylist();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.println("Playlist does not exist.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    tempSong = userSong(songMap, artistMap, albumMap);
                    if (playlistMap.get(playlistName).deleteSong(tempSong)) {
                        System.out.println("Song deleted from playlist.");

                        lib.deleteSong(tempSong);
                        songMap.remove(tempSong.name.toLowerCase());

                        Connection connection = null;
                        try {
                            StringBuilder buf = new StringBuilder();
                            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
                            Statement statement = connection.createStatement();
                            statement.setQueryTimeout(30);
                            buf.append("delete from songs where name = \"");
                            buf.append(tempSong.name);
                            buf.append("\"");
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
                    } else {
                        System.out.println("Song not found in playlist.");
                    }
                    userInterfaceScanner.nextLine();
                    break;

                // Creates a new playlist based on a specific artist
                case 9:
                    tempPlaylist = new Playlist();
                    userInterfaceScanner.nextLine();
                    System.out.println("Create a playlist from a specific artist: ");
                    System.out.print("Enter the artist you'd like to create a playlist of: ");
                    String playlistArtist = userInterfaceScanner.nextLine();
                    for (Song song:lib.getSongs()) {
                        if (song.getArtist().name.toLowerCase().equals(playlistArtist.toLowerCase())) {
                            tempPlaylist.addSong(song);
                        }
                    }

                    System.out.println("The new playlist must be named:");
                    playlistName = whichPlaylist();
                    playlistMap.put(playlistName, tempPlaylist);
                    System.out.println("Playlist \'" + playlistName + "\' created.");
                    break;

                // Displays all songs in a playlist to the console
                case 10:
                    System.out.println("Displaying songs in a playlist:");
                    playlistName = whichPlaylist();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.println("Playlist does not exist.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    System.out.println(playlistMap.get(playlistName).getSongs());
                    userInterfaceScanner.nextLine();
                    break;

                // Generates an XML file from the songs in a playlist
                case 11:
                    System.out.println("Generating XML file from playlist");
                    playlistName = whichPlaylist();
                    StringBuilder buf = new StringBuilder();
                    buf.append(playlistName);
                    buf.append(".xml");
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.println("Playlist does not exist.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    File playListFile = new File(buf.toString());
                    PrintWriter outputPlaylistFile = new PrintWriter(playListFile);
                    outputPlaylistFile.println(playlistMap.get(playlistName).toXML());
                    outputPlaylistFile.close();
                    userInterfaceScanner.nextLine();
                    break;

            }
            System.out.print("Enter \"y\" to select another menu option: ");
            selectOptionAgain = userInterfaceScanner.nextLine();
        } while (selectOptionAgain.equalsIgnoreCase("y"));
    }

    /**
     * Creates a Song object using user input. Also checks
     * the Artist and Album Maps for duplicates of
     * those objects
     *
     * @param songMap the Song Map to be used during the program
     * @param artistMap the Artist Map to be used during the program
     * @param albumMap the Album Map to be used during the program
     * @return
     */
    private static Song userSong(Map songMap, Map artistMap, Map albumMap) {
        Song tSong;
        String temp;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the song's title: ");
        temp = scanner.nextLine();
        tSong = new Song(temp);

        System.out.print("Enter the song's artist: ");
        temp = scanner.nextLine();

        if (!artistMap.containsKey(temp.toLowerCase())) {
            Artist tempArtist = new Artist(temp);
            tSong.setArtist(tempArtist);
            artistMap.put(temp.toLowerCase(), tempArtist);
            tempArtist.toSQL();
        } else {
            tSong.setArtist((Artist)artistMap.get(temp.toLowerCase()));
        }

        System.out.print("Enter the song's album: ");
        temp = scanner.nextLine();

        if (!albumMap.containsKey(temp.toLowerCase())) {
            Album tempAlbum = new Album(temp);
            tempAlbum.setArtist(tSong.getArtist());
            tSong.setAlbum(tempAlbum);
            albumMap.put(temp.toLowerCase(), tempAlbum);
            tempAlbum.toSQL();
        } else {
            tSong.setAlbum((Album)albumMap.get(temp.toLowerCase()));
        }

        return tSong;
    }

    /**
     * Returns a String representing the name of the playlist
     * a user would like to access.
     *
     * @return a String representing the name of the playlist
     */
    private static String whichPlaylist() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the playlist's name: ");
        return scanner.nextLine();
    }

    /**
     * Loads the songs from a SQL database table into the library
     * and the Song Map
     *
     * @param library the Library object used in the program
     * @param songMap the Song Map used in the program
     */
    private static void loadSongFromSQL(Library library, Map songMap) {
        List<Song> songSQLList = new ArrayList<>();
        songSQLList = new Song().fromSQL();
        for(Song song:songSQLList) {
            library.addSong(song);
            songMap.put(song.name.toLowerCase(), song);
        }
    }

    /**
     * Loads the songs from a SQL database table into the Artist Map
     *
     * @param artistMap the Artist Map used in the program
     */
    private static void loadArtistFromSQL(Map artistMap) {
        List<Artist> artistSQLList = new ArrayList<>();
        artistSQLList = new Artist().fromSQL();
        for(Artist artist:artistSQLList) {
            artistMap.put(artist.name.toLowerCase(), artist);
        }
    }

    /**
     * Loads the songs from a SQL database table into the Album Map
     *
     * @param albumMap the Album Map used in the program
     */
    private static void loadAlbumFromSQL(Map albumMap) {
        List<Album> albumSQLList = new ArrayList<>();
        albumSQLList = new Album().fromSQL();
        for(Album album:albumSQLList) {
            albumMap.put(album.name.toLowerCase(), album);
        }
    }
}
