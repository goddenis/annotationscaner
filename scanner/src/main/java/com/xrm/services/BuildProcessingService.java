package com.xrm.services;

import com.xrm.domain.ChangeSet;
import com.xrm.domain.Project;
import com.xrm.domain.ProjectUpdate;
import com.xrm.repositories.ChangeSetRepository;
import com.xrm.repositories.ProjectRepository;
import com.xrm.repositories.ProjectUpdateRepository;
import com.xrm.utils.ClassExtractor;
import com.xrm.utils.HashSumFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class BuildProcessingService {

    public static final String ANNOTATION_NAME = "com.xrm.annotations.CodeUpdateAnnotaition";

    private static final String DATE_METHOD_NAME = "date";
    private static final String DESCRIPTION_METHOD_NAME = "description";
    private static final String AUTHOR_METHOD_NAME = "author";
    @Autowired
    private HashSumFactory hashSumFactory;
    @Autowired
    private ProjectUpdateRepository projectUpdatesRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ChangeSetRepository changeSetRepository;

    @Autowired
    private ClassExtractor classExtractor;

    public Project process() {
        return new Project(1l, "Stub", "Stub");
    }

    public Project process(MultipartFile file) {
        return process(file.getOriginalFilename(), file);
    }


    public Project process(Long id, MultipartFile file) {
        Project one = projectRepository.findOne(id);
        if(one != null){
            return process(one, file);
        }
        return null;
    }
    public Project process(String projectName, MultipartFile file) {

        Project project = projectRepository
                .findOneByNameAndFileName(projectName,file.getOriginalFilename())
                .orElse(new Project(projectName, file.getOriginalFilename()));
        if (project.getId() == null) {
            projectRepository.save(project);
        }

        return process(project,file);
    }

    public Project process(Project project, MultipartFile file) {
        try {
            String checksum = hashSumFactory.getMD5Checksum(file.getBytes());

            if (file.isEmpty())
                throw new ProccesingException("File is empty");

            Path newFile = Paths.get(System.getProperty("user.dir")).resolve(UUID.randomUUID().toString() + ".jar");
            Files.copy(file.getInputStream(), newFile);

                ChangeSet changeSet = changeSetRepository.findByFileHash(checksum).orElse(new ChangeSet(checksum, new Date(Instant.now().toEpochMilli()), project));

                if (changeSet.getId() != null)
                    return project;
                else
                    changeSetRepository.save(changeSet);



                List<? extends Class<?>> annotatedClasses = classExtractor.extractClasses(newFile);

                annotatedClasses.stream().forEach(annotatedClass -> {
                    Arrays.asList(annotatedClass.getAnnotations()).stream()
                            .filter(annotation -> ANNOTATION_NAME.equals(annotation.annotationType().getName()))
                            .map(a -> {
                                try {
                                    return new ProjectUpdate(
                                            annotatedClass.getName(),
                                            a.annotationType().getMethod(DATE_METHOD_NAME).invoke(a).toString(),
                                            a.annotationType().getMethod(DESCRIPTION_METHOD_NAME).invoke(a).toString(),
                                            a.annotationType().getMethod(AUTHOR_METHOD_NAME).invoke(a).toString(),
                                            changeSet
                                    );

                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            })
                            .filter(projectUpdate -> projectUpdate != null)
                            .forEach(projectUpdate -> projectUpdatesRepository.save(projectUpdate));
                });
            File delFile = newFile.toFile();
            newFile = null;
            delFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return project;
    }
}
