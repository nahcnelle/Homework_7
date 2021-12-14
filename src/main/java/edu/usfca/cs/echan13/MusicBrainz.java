package edu.usfca.cs.echan13;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;

public class MusicBrainz {

    public static String getContent(Node n) {
        StringBuilder sb = new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();
    }

    public static String artistSearch(String artistName) {
        String offArtistName = "";
        String initialURL = "https://musicbrainz.org/ws/2/artist?query=" + artistName + "&fmt=xml";
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
            NodeList artists = doc.getElementsByTagName("artist-list");
            /* let's assume that the one we want is first. */
            Node artistNode = artists.item(0).getFirstChild();
            offArtistName = getContent(artistNode.getFirstChild());
            // to find id
//            Node artistIDNode = artistNode.getAttributes().getNamedItem("id");
//            String id = artistIDNode.getNodeValue();

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return offArtistName;
    }

    public static String albumSearch(String albumName) {
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
            System.out.println(artistName);

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return offAlbumName;
    }

    public static String songSearch(String songName) {
        String offAlbumName = "";
        String artistName = "";
        Node subNode;
        String initialURL = "https://musicbrainz.org/ws/2/release-group/?query=album+AND+" + songName.replaceAll(" ", "+");
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
            System.out.println(artistName);

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return offAlbumName;
    }

    public static void main(String[] args) {
        //TheAudioDBExample();
        System.out.println(artistSearch("Beatles"));
        System.out.println(albumSearch("after hours"));

    }
}
