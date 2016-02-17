
@echo off
echo Waiting for file handle to be closed ...
ping 127.0.0.1 -n 5 -w 1000 > NUL
move /Y "C:\Users\Frederic\Documents\GitHub\JukePi\YTMusicBox\youtube-dl.exe.new" "C:\Users\Frederic\Documents\GitHub\JukePi\YTMusicBox\youtube-dl.exe" > NUL
echo Updated youtube-dl to version 2016.02.13.
start /b "" cmd /c del "%~f0"&exit /b"
                
