#JukePi 
###*A Client-Server based Application (not only) for the Raspberry Pi*


##Description
This program suite includes a network based music and video playback tool using the Raspberry Pi as a media
 player. [Youtube-DL](http://rg3.github.io/youtube-dl/ "Homepage of the creators of youtube-dl") can be used to parse
 and playback music and video material from many different websites (see the 
 [List of supported sites](http://rg3.github.io/youtube-dl/supportedsites.html)).
The software is written in Java and is separated in multiple programs, wich can run on different devices.
These programs are:  
###A server 
The server manages the track lists and multiple clients and parses the music and video links.
The server differentiates between wish list and gap list. Music/video wishes of the clients are added to the
wish list and tracks from this list are played with a higher priority. If the wish list is empty, tracks from
the gap list are played. Both lists can be modified through the clients and gap list can also be saved on
the server for fast switching. The server can run on desktop and on raspberry pi. 
###A client
The client controls the playback and edits the track lists of the server.
They can have different permissions so not every modification can be done with every client (see below).
They can be executed on desktop, raspberry pi (with x-server running) or android.
###A player
The player plays the music and video links in the track lists and is also a client of the server. 
It can currently be executed only on the raspberry pi.



##More...

The software is currently under development and not all features are implemented, yet. 
Currently server and client for desktop are stable and running, as well as the client player for raspberry pi.
Client permissions are not implemented yet, so every client can currently modify everything on the server.
The Android client can add tracks to the wish list and display playback data from the server.