package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XMLParsing {

    public static String getContent(Node n) {
        StringBuilder sb = new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();
    }

    public boolean xmlP(Library library, String filename) {
        Song currentSong;
        Artist currentArtist;
        Album currentAlbum;
        int artistCount = 0;
        int albumCount = 0;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));

            Element root = doc.getDocumentElement();
            NodeList songs = root.getElementsByTagName("song");
            Node currentNode, subNode;

            for (int i = 0; i < songs.getLength(); i++) {
                currentNode = songs.item(i);
                NodeList children = currentNode.getChildNodes();
                currentSong = new Song();
                for (int j = 0; j < children.getLength(); j++) {
                    subNode = children.item(j);
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (currentNode.getNodeName().equals("song")) {
                            currentSong.setEntityID(Integer.valueOf(currentNode.getAttributes().getNamedItem("id").getNodeValue()));
                        }
                        if (subNode.getNodeName().equals("title")) {
                            currentSong.setName(getContent(subNode).trim());
                        }
                        if (subNode.getNodeName().equals("artist")) {
                            currentArtist = new Artist(getContent(subNode).trim());
                            currentArtist.setEntityID(Integer.valueOf(subNode.getAttributes().getNamedItem("id").getNodeValue()));
                            currentSong.setArtist(currentArtist);
                            artistCount++;
                        }
                        if (subNode.getNodeName().equals("album")) {
                            currentAlbum = new Album(getContent(subNode).trim());
                            currentAlbum.setEntityID(Integer.valueOf(subNode.getAttributes().getNamedItem("id").getNodeValue()));
                            currentSong.setAlbum(currentAlbum);
                            albumCount++;
                        }
                    }
                }
                library.addSong(currentSong);
            }

            NodeList artists = root.getElementsByTagName("artist");
            for (int i = artistCount; i < artists.getLength(); i++) {
                currentNode = artists.item(i);
                NodeList children = currentNode.getChildNodes();
                currentArtist = new Artist();
                for (int j = 0; j < children.getLength(); j++) {
                    subNode = children.item(j);
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (subNode.getNodeName().equals("name")) {
                            currentArtist.setName(getContent(subNode).trim());
                            currentArtist.setEntityID(Integer.valueOf(currentNode.getAttributes().getNamedItem("id").getNodeValue()));
                        }
                    }
                }
            }

            NodeList albums = root.getElementsByTagName("album");
            for (int i = albumCount; i < albums.getLength(); i++) {
                currentNode = albums.item(i);
                NodeList children = currentNode.getChildNodes();
                currentAlbum = new Album();
                for (int j = 0; j < children.getLength(); j++) {
                    subNode = children.item(j);
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (subNode.getNodeName().equals("title")) {
                            currentAlbum.setName(getContent(subNode).trim());
                            currentAlbum.setEntityID(Integer.valueOf(currentNode.getAttributes().getNamedItem("id").getNodeValue()));
                        }
                    }
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
            return false;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println("Parser error");
            return false;
        }
    }
}
