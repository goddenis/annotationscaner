package com.xrm.utils;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

/**
 * Created by goddenis on 29.11.2016.
 */
public class JarFileCreator {

    public JarFile getJar(File path) throws IOException {
        return new JarFile(path);
    }

}
