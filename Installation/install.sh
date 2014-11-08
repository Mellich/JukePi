#!/bin/sh

print "Note, that youtube-dl and omxplayer have to be installed on your system to run JBServer correctly!"
sudo cp jbserver /usr/local/bin/jbserver
sudo cp jbserverd /etc/init.d/jbserverd

mkdir /home/pi/.jbserver

cp server.jar /home/pi/.jbserver

sudo chmod +x /etc/init.d/jbserverd

sudo update-rc.d /etc/init.d/jbserverd defaults
print "Installation done"