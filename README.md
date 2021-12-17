#### CS 514
#### Homework #7: Putting it all together

This program is a text-based interface for users to interact with a music library.
Running the main method in the UserInterface class loads the songs in the "music.db"
SQL database and displays menu options for the music library on the console. Options 
include adding and deleting songs from the music library or playlist, displaying songs
to the console, and generating XML files from the songs in the library or playlist.
When adding to the library without all the details, the MusicBrainz database will be 
searched and the first result will be returned. Follow the steps on the console to 
interact with the library and playlists. Songs added or deleted from the library are
so added or deleted from music.db.

The Song, Artist, and Album class all extend the Entity class. The Entity class stores
data for the entity's name, the date it was created, the entity's id, and the total number
of entities created. The Song class stores data for a song's title, artist, album, and 
if it is a liked song. The Artist class stores data for an artist's name, list of albums,
number of songs, and number of albums. The Album class stores data for an album's artist,
list of songs, and number of songs.

The Library class represents a music library and stores a list of Song objects. It contains 
methods to add and delete to the library, find specific songs, and generate XML of the songs
in the library. The Playlist class similarly stores a list of Song objects and has methods to add, delete, and find songs and generate XML of the songs in the playlist. There are also additional methods to shuffle, sort by liked songs, and merge two playlists.