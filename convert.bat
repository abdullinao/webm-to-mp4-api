echo off
set filename=%1


ffmpeg\bin\ffmpeg.exe -y -i videos/toconvert/%filename% -strict experimental -vf "crop=trunc(iw/2)*2:trunc(ih/2)*2" videos/toupload/%filename%.mp4

exit