package edu.forfun.webmtomp4service.Controlelrs;


import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Base64;

@RestController

public class restController {

    @GetMapping(value = "/getfile/{urlInB64}")
    public void getImageAsByteArray(HttpServletResponse response, @PathVariable(required = false) String urlInB64) throws IOException, InterruptedException {

        URL url = new URL(new String(Base64.getDecoder().decode(urlInB64)));
//ffmpeg -i videos\ToConvert\1.webm -strict experimental -vf "crop=trunc(iw/2)*2:trunc(ih/2)*2"   videos\Converted\video.mp4
//ffmpeg -i 1.webm -strict experimental -vf "crop=trunc(iw/2)*2:trunc(ih/2)*2" video.mp4
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        String fileName = url.toString().substring(url.toString().lastIndexOf("/") + 1);
        FileOutputStream fos = new FileOutputStream("F:\\converterApi\\tocon\\" + fileName);//+ ".mp4"
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();

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

        Process p = Runtime.getRuntime().exec("cmd /c  start /wait  C:\\convert.bat " + fileName);


        System.out.println("Waiting for batch file ...");
        p.waitFor();
        System.out.println("Batch file done.");
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


        InputStream in = new FileInputStream("F:\\converterApi\\tosend\\" + fileName+ ".mp4");
        response.setContentType(MediaType.ALL_VALUE);
        IOUtils.copy(in, response.getOutputStream());
        in.close();
    }

}
