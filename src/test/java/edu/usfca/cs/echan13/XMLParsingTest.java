package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XMLParsingTest {
    XMLParsing xmlP;
    Library lib;

    @BeforeEach
    void setUp() {
        xmlP = new XMLParsing();
        lib = new Library();
    }

    @Test
    void xmlP() {
        xmlP.xmlP(lib, "music-library.xml");
        for (Song song:lib.getSongs()) {
            System.out.println(song);
        }
    }
}