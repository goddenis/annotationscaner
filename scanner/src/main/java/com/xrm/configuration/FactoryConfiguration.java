package com.xrm.configuration;

import com.xrm.services.ClassLoaderFactory;
import com.xrm.utils.ClassExtractor;
import com.xrm.utils.HashSumFactory;
import com.xrm.utils.JarFileCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;

@Configuration
public class FactoryConfiguration {

    @Bean
    public ClassLoaderFactory getClassLoaderFactory(){
        return new ClassLoaderFactory();
    }
    @Bean
    public HashSumFactory getMessageDigest() throws NoSuchAlgorithmException {
        return new HashSumFactory();
    }
    @Bean
    public ClassExtractor getExtractot(){
        return new ClassExtractor();
    }

    @Bean
    public JarFileCreator getJarFileCreator(){
        return new JarFileCreator();
    }
}
