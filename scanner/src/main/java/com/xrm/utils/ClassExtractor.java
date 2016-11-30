package com.xrm.utils;

import com.xrm.services.ClassLoaderFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static com.xrm.services.BuildProcessingService.ANNOTATION_NAME;

public class ClassExtractor {
    @Autowired
    private ClassLoaderFactory classLoaderFactory;

    @Autowired
    private JarFileCreator jarFileCreator;

    public List<Class<?>>  extractClasses( ClassLoader cl, JarFile jarFile){

        return jarFile
                .stream().filter(jarEntry -> jarEntry.getName().endsWith(".class"))
                .map(jarEntry -> jarEntry.getName().substring(0, jarEntry.getName().length() - 6).replace('/', '.'))
                .map(clasName -> {
                    try {
                        return cl.loadClass(clasName);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(aClass ->
                        aClass != null &&
                                Arrays.asList(aClass.getAnnotations())
                                        .stream().anyMatch(annotation -> ANNOTATION_NAME.equals(annotation.annotationType().getName())))
                .collect(Collectors.toList());

    }

    public List<Class<?>> extractClasses(Path newFile) throws IOException {
        URLClassLoader classLoader = classLoaderFactory.getClassLoader(newFile.toAbsolutePath().toString());

        List<Class<?>> classes = extractClasses(classLoader, jarFileCreator.getJar(newFile.toFile()));
        classLoader.close();
        return classes;

    }
}
