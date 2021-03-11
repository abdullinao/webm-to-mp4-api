package edu.forfun.webmtomp4service.Controlelrs;


import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Base64;

@RestController

public class restController {

    @GetMapping(value = "/getfile/{urlInB64}")
    public void getImageAsByteArray(HttpServletResponse response, @PathVariable(required = false) String urlInB64) throws IOException {

        URL url = new URL(new String(Base64.getDecoder().decode(urlInB64)));

        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        String fileName = url.toString().substring(url.toString().lastIndexOf("/") + 1);
        FileOutputStream fos = new FileOutputStream("videos/ToConvert/" + fileName);//+ ".mp4"
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        InputStream in = new FileInputStream("videos/Converted/" + fileName);
        response.setContentType(MediaType.ALL_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

}
