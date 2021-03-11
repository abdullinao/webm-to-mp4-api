package edu.forfun.webmtomp4service.Model;

import java.io.File;

public class response {

    public File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public response(File file) {
        this.file = file;
    }
}
