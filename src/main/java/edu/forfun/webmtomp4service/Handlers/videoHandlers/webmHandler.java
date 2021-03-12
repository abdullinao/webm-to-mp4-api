package edu.forfun.webmtomp4service.Handlers.videoHandlers;

import edu.forfun.webmtomp4service.Controlelrs.restController;
import edu.forfun.webmtomp4service.Handlers.filesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Base64;

@Component
public class webmHandler implements IVideoHandler {

    @Override
    public String convertVideoAndReturnLocalFilePath(String base64URL) {
//todo переместить всю структуру в одну папку
        Logger logger = LoggerFactory.getLogger(webmHandler.class);
        logger.debug("started webm 2 mp4");
        try {
            filesHandler.checkFolder();
            logger.debug("folders checked");
        } catch (Exception e) {
            logger.error(e.toString());
        }

        try {
            logger.debug("started converting magic");
            URL url = new URL(new String(Base64.getDecoder().decode(base64URL)));
//ffmpeg -i videos\ToConvert\1.webm -strict experimental -vf "crop=trunc(iw/2)*2:trunc(ih/2)*2"   videos\Converted\video.mp4
//ffmpeg -i 1.webm -strict experimental -vf "crop=trunc(iw/2)*2:trunc(ih/2)*2" video.mp4

            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            String fileName = url.toString().substring(url.toString().lastIndexOf("/") + 1);
            logger.debug("file name: {}", fileName);
            FileOutputStream fos = new FileOutputStream("videos\\toconvert\\" + fileName);//+ ".mp4"
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            logger.debug("calling for ffmpeg");
            Process p = Runtime.getRuntime().exec("cmd /c  start /wait  convert.bat " + fileName);
            logger.debug("waiting for cmd to complete job");
            p.waitFor();
            logger.debug("file is done");

            return "videos\\toupload\\" + fileName + ".mp4";

        } catch (Exception e) {

            logger.error(e.toString());
        }

        return null;
    }
}
