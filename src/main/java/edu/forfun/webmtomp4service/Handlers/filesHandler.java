package edu.forfun.webmtomp4service.Handlers;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class filesHandler {


    public void deleteFiles() {

    }


    public static void checkFolder() {
        File videos2convert = new File("videos/toconvert");
        File videos2upload = new File("videos/toupload");
        if (!videos2convert.exists() || !videos2upload.exists()){
            videos2convert.mkdirs();
            videos2upload.mkdirs();
        }

    }




}
