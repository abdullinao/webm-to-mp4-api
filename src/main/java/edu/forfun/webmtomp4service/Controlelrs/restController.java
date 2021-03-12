package edu.forfun.webmtomp4service.Controlelrs;


import edu.forfun.webmtomp4service.Handlers.videoHandlers.webmHandler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController

public class restController {
    private final Logger logger = LoggerFactory.getLogger(restController.class);


    @Autowired
    private webmHandler webmHandler;

    /**
     * @param urlInB64 - урл до вебм закодированный в BASE64, например https://test.io/webmfile.webm = aHR0cHM6Ly90ZXN0LmlvL3dlYm1maWxlLndlYm0=
     * @apiNote запрос к данному урлу вернет переконвертированный в mp4 видео файл без расширения (на стороне клиента нужно добавить .mp4)
     */

    @GetMapping(value = "/webm2mp4/{urlInB64}")
    public void getImageAsByteArray(HttpServletResponse response, @PathVariable(required = false) String urlInB64) {
        try {
            logger.debug("received video: {}", urlInB64);
            InputStream in = new FileInputStream(webmHandler.convertVideoAndReturnLocalFilePath(urlInB64));
            logger.debug("done working with converter");
            response.setContentType(MediaType.ALL_VALUE);
            logger.debug("content type edited");
            IOUtils.copy(in, response.getOutputStream());
            logger.debug("copied file to response");
            in.close();
            logger.debug("done with video: {}", urlInB64);
        } catch (Exception e) {
            logger.error(e.toString());
        }

    }

}
//Thread.sleep(5000);
//        System.out.println("1");
//
//        System.out.println("2");
//        Process process = Runtime.getRuntime().exec("C:\\ffmpeg\\bin\\ffmpeg -i F://converterApi/tocon/" + fileName +
//                " -strict experimental -vf \"crop=trunc(iw/2)*2:trunc(ih/2)*2\" " +
//                "  F://converterApi/tosend/" + fileName + ".mp4");
//        System.out.println("3");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        System.out.println("4");
//        String line = "";
//        System.out.println("5");
//        System.out.println(line);
//        while ((line = reader.readLine()) != null) {
//            System.out.println("6");
//            System.out.println(line);
//        }
//        System.out.println("7");
//        ProcessBuilder pb=new ProcessBuilder("C:\\ffmpeg\\bin\\ffmpeg -i F:\\converterApi\\tocon\\" + fileName +
//                " -strict experimental -vf \"crop=trunc(iw/2)*2:trunc(ih/2)*2\" " +
//                "  F:\\converterApi\\tosend\\" + fileName + ".mp4");
//        pb.redirectErrorStream(true);
//        Process process=pb.start();
//        BufferedReader inStreamReader = new BufferedReader(
//                new InputStreamReader(process.getInputStream()));
//
//        while(inStreamReader.readLine() != null){
//            System.out.println(inStreamReader.readLine());
//        }

//https://stackabuse.com/executing-shell-commands-with-java/


// C:\ffmpeg\bin
//        String[] cmd = {"C:\\ffmpeg\\bin\\ffmpeg", "-i", "F:\\converterApi\\tocon\\" + fileName,
//                " -strict experimental -vf \"crop=trunc(iw/2)*2:trunc(ih/2)*2\" ",
//                "F:\\converterApi\\tosend\\" + fileName + ".mp4"};
//        Runtime.getRuntime().exec(cmd);
////
//    Runtime.getRuntime().exec("C:\\ffmpeg\\bin\\ffmpeg -i F://converterApi/tocon/" + fileName +
//                 " -strict experimental -vf \"crop=trunc(iw/2)*2:trunc(ih/2)*2\" " +                      //workiing
//                 "  F://converterApi/tosend/" + fileName + ".mp4");
