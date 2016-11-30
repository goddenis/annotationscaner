package com.xrm.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class ClassLoaderFactory {
    public URLClassLoader getClassLoader(String url) throws MalformedURLException {
        URL[] urls = {new URL("jar:file:" + url + "!/")};
        return URLClassLoader.newInstance(urls);
    }
}
