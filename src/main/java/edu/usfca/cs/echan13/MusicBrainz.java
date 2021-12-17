package edu.usfca.cs.echan13;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 *  The MusicBrainz class fetches data from the
 *  MusicBrainz website to fill in missing information
 *  for Song, Artist, and Album objects. Methods to find
 *  songs, artists, and albums are available for this purpose.
 *
 *  @author Ellen Chan
 *
 */

public class MusicBrainz {

    /**
     * Returns a String representing the relevant data from MusicBrainz.
     *
     * @param n the Node containing data from MusicBrainz
     * @return a String representing the data stored in the Node
     */
    public static String getContent(Node n) {
        StringBuilder sb = new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();
    }

    /**
     * Searches the MusicBrainz database for a specific artist and
     * returns a Map storing the first found artist's name
     *
     * @param artistName the specific artist to search for
     * @return a Map containing the artist's name
     */
    public static Map<String, String> artistSearch(String artistName) {
        Map<String, String> artistMap = new HashMap<>();
        String offArtistName = "";
        String initialURL = "https://musicbrainz.org/ws/2/artist?query=" + artistName + "&fmt=xml";

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if threre's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (echan13@dons.usfca.edu");

            Document doc = db.parse(u.getInputStream());
            NodeList artists = doc.getElementsByTagName("artist-list");
            /* let's assume that the one we want is first. */
            Node artistNode = artists.item(0).getFirstChild();
            offArtistName = getContent(artistNode.getFirstChild());

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        artistMap.put("artistName", offArtistName);
        return artistMap;
    }

    /**
     * Searches the MusicBrainz database for a specific album and
     * returns a Map storing the first found album title and its
     * artist's name.
     *
     * @param albumName the specific album to search for
     * @return a Map containing the album title and the artist's name
     */
    public static Map<String, String> albumSearch(String albumName) {
        Map<String, String> albumMap = new HashMap<>();
        String offAlbumName = "";
        String artistName = "";
        Node subNode;
        String initialURL = "https://musicbrainz.org/ws/2/release-group/?query=album+AND+" + albumName.replaceAll(" ", "+");
        /* MusicBrainz gives each element in their DB a unique ID, called an MBID. We'll use this to fecth that. */

        /* now let's parse the XML.  */
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if threre's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (echan13@dons.usfca.edu");

            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
            NodeList albums = doc.getElementsByTagName("release-group-list");
            /* let's assume that the one we want is first. */
            Node albumNode = albums.item(0).getFirstChild();
            NodeList albumAttributes = albumNode.getChildNodes();
            offAlbumName = getContent(albumNode.getFirstChild());
            for (int i = 0; i < albumAttributes.getLength(); i++) {
                subNode = albumAttributes.item(i);
                if (subNode.getNodeName().equals("artist-credit")) {
                    artistName = getContent(subNode.getFirstChild().getFirstChild());
                }
            }
        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        albumMap.put("albumName", offAlbumName);
        albumMap.put("artistName", artistName);
        return albumMap;
    }

    /**
     * Searches the MusicBrainz database for a specific artist and
     * returns a Map storing the first found song title, album title,
     * and its artist's name.
     *
     * @param songName the specific song to search for
     * @return a Map containing the song title, album title, and the artist's name
     */
    public static Map<String, String> songSearch(String songName) {
        Map<String, String> songMap = new HashMap<>();
        String offSongName = "";
        String albumName = "";
        String artistName = "";
        Node subNode;
        String initialURL = "https://musicbrainz.org/ws/2/recording/?query=" + songName.replaceAll(" ", "+");
        /* MusicBrainz gives each element in their DB a unique ID, called an MBID. We'll use this to fecth that. */

        /* now let's parse the XML.  */
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if threre's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (echan13@dons.usfca.edu");

            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
            NodeList songs = doc.getElementsByTagName("recording-list");
            /* let's assume that the one we want is first. */
            Node songNode = songs.item(0).getFirstChild();
            NodeList songAttributes = songNode.getChildNodes();
            offSongName = getContent(songNode.getFirstChild());
            for (int i = 0; i < songAttributes.getLength(); i++) {
                subNode = songAttributes.item(i);
                if (subNode.getNodeName().equals("artist-credit")) {
                    artistName = getContent(subNode.getFirstChild().getFirstChild());
                }
                if (subNode.getNodeName().equals("release-list")) {
                    albumName = getContent(subNode.getFirstChild().getFirstChild());
                }
            }

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        songMap.put("songName", offSongName);
        songMap.put("albumName", albumName);
        songMap.put("artistName", artistName);
        return songMap;
    }
}
