package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

    public static void main(String[] args) {
        Library lib = new Library();
        Song tempSong;
        Playlist tempPlaylist;
        Map<String, Playlist> playlistMap = new HashMap<>();
        Map<String, Artist> artistMap = new HashMap<>();
        Map<String, Album> albumMap = new HashMap<>();
        Scanner userInterfaceScanner = new Scanner(System.in);
        String filename, playlistName, playlistName2;
        String selectOptionAgain;

        System.out.println("-Music Library-");
        do {
            // prints the menu of options to the console
            System.out.println("1. Load songs to the library from an XML file");
            System.out.println("2.");
            System.out.println("3. Add a song to the library");
            System.out.println("4. Delete a song from the library");
            System.out.println("5. Remove duplicate songs from the library");
            System.out.println("6. Search the library for a song");
            System.out.println("7. Display all songs in library");
            System.out.println("8. Add a song to a playlist");
            System.out.println("9. Delete a song from a playlist");
            System.out.println("10. Merge two playlists");
            System.out.println("11. Sort playlist by liked songs");
            System.out.println("12. Shuffle a playlist");
            System.out.println("13. Search a playlist for a song");
            System.out.println("14. Display all songs in a playlist");
            System.out.print("-Select a number from the options above: ");

            // executes menu selection based on user input
            switch (userInterfaceScanner.nextInt()) {
                case 1:
                    userInterfaceScanner.nextLine();
                    System.out.print("Enter the XML filename: ");
                    filename = userInterfaceScanner.nextLine();
                    XMLParsing xmlParse = new XMLParsing();
                    if (xmlParse.xmlP(lib, filename)) {
                        System.out.println("Songs added to library.");
                    }
                    break;
                case 3:
                    System.out.println("Song to add to library:");
                    System.out.print("Do you know all of the song info (such as song title, artist name, and album)? (y/n)");
                    if (userInterfaceScanner.nextLine().equals("y")){
                        tempSong = userSong();
                    } else {
                        System.out.println("1. Song Title \n2. Artist Name \n3. Album Title\n" +
                                "Select which of the above you know: ");
                        String tempData;
                        switch (userInterfaceScanner.nextInt()) {
                            case 1:
                                System.out.println("Enter the song title: ");
                                tempData = userInterfaceScanner.nextLine();
                                break;
                        }
                    }
                    tempSong = userSong();
                    lib.addSong(tempSong);
                    System.out.println("Song added to library.");
                    userInterfaceScanner.nextLine();
                    break;
                case 4:
                    System.out.println("Song to delete from library:");
                    tempSong = userSong();
                    if (lib.deleteSong(tempSong)) {
                        System.out.println("Song deleted.");
                    } else {
                        System.out.println("Song not found in library.");
                    }
                    userInterfaceScanner.nextLine();
                    break;
                case 5:
                    lib.removeDupes();
                    System.out.println("Duplicate songs removed.");
                    userInterfaceScanner.nextLine();
                    break;
                case 6:
                    System.out.println("Song to search for: ");
                    tempSong = userSong();
                    if (lib.findSong(tempSong)) {
                        System.out.println("Song is in library.");
                    } else {
                        System.out.println("Song not found in library.");
                    }
                    userInterfaceScanner.nextLine();
                    break;
                case 7:
                    System.out.println("Songs in library:");
                    System.out.println(lib.getSongs());
                    userInterfaceScanner.nextLine();
                    break;
                case 8:
                    System.out.println("Add song to a playlist:");
                    playlistName = whichPlaylist();
                    userInterfaceScanner.nextLine();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.print("Playlist does not exist. Would you like create a new playlist and add a song? (y/n) ");
                        if (userInterfaceScanner.nextLine().equals("y")) {
                            tempSong = userSong();
                            tempPlaylist = new Playlist();
                            tempPlaylist.addSong(tempSong);
                            playlistMap.put(playlistName, tempPlaylist);
                            System.out.println("Song added to playlist.");
                            break;
                        }
                        break;
                    }
                    tempSong = userSong();
                    playlistMap.get(playlistName).addSong(tempSong);
                    System.out.println("Song added to playlist.");
                    break;
                case 9:
                    System.out.println("Delete a song from a playlist:");
                    playlistName = whichPlaylist();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.println("Playlist does not exist.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    tempSong = userSong();
                    if (playlistMap.get(playlistName).deleteSong(tempSong)) {
                        System.out.println("Song deleted from playlist.");
                    } else {
                        System.out.println("Song not found in playlist.");
                    }
                    userInterfaceScanner.nextLine();
                    break;
                case 10:
                    System.out.println("Two playlists are needed to merge playlists.");
                    System.out.println("First playlist:");
                    playlistName = whichPlaylist();
                    System.out.println("Second playlist:");
                    playlistName2 = whichPlaylist();
                    if (!playlistMap.containsKey(playlistName) || !playlistMap.containsKey(playlistName2)) {
                        System.out.println("Unable to merge playlists. Two existing playlists are needed.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    tempPlaylist = playlistMap.get(playlistName).mergePlaylists(playlistMap.get(playlistName2));
                    System.out.println("Playlists merged. Please name the new playlist.");
                    playlistName = whichPlaylist();
                    playlistMap.put(playlistName, tempPlaylist);
                    userInterfaceScanner.nextLine();
                    break;
                case 11:
                    System.out.println("Sort a playlist by liked songs:");
                    playlistName = whichPlaylist();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.println("Playlist does not exist.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    playlistMap.get(playlistName).sortLiked();
                    System.out.println("Playlist sorted by liked songs.");
                    userInterfaceScanner.nextLine();
                    break;
                case 12:
                    System.out.println("Shuffle a playlist:");
                    playlistName = whichPlaylist();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.println("Playlist does not exist.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    playlistMap.get(playlistName).shuffle();
                    System.out.println("Playlist shuffled");
                    userInterfaceScanner.nextLine();
                    break;
                case 13:
                    System.out.println("Songs in a playlist:");
                    playlistName = whichPlaylist();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.println("Playlist does not exist.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    System.out.println("Song to search for: ");
                    tempSong = userSong();
                    if (playlistMap.get(playlistName).findSong(tempSong)) {
                        System.out.println("Song is in library.");
                    } else {
                        System.out.println("Song not found in library.");
                    }
                    userInterfaceScanner.nextLine();
                    break;
                case 14:
                    System.out.println("Songs in a playlist:");
                    playlistName = whichPlaylist();
                    if (!playlistMap.containsKey(playlistName)) {
                        System.out.println("Playlist does not exist.");
                        userInterfaceScanner.nextLine();
                        break;
                    }
                    System.out.println(playlistMap.get(playlistName).getSongs());
                    userInterfaceScanner.nextLine();
                    break;
            }
            System.out.print("Enter \"y\" to select another menu option: ");
            selectOptionAgain = userInterfaceScanner.nextLine();
        } while (selectOptionAgain.equalsIgnoreCase("y"));
    }

    private static Song userSong() {
        Song tSong;
        String temp;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the song's title: ");
        temp = scanner.nextLine();
        tSong = new Song(temp);
        System.out.print("Enter the song's artist: ");
        temp = scanner.nextLine();
        tSong.getArtist().setName(temp);
        System.out.print("Enter the song's album: ");
        temp = scanner.nextLine();
        tSong.getAlbum().setName(temp);

        return tSong;
    }

    private static String whichPlaylist() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the playlist's name: ");
        return scanner.nextLine();
    }

}
