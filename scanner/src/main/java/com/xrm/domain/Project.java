package com.xrm.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String fileName;

    public Project(Long id, String name, String fileName) {
        this.id = id;
        this.name = name;
        this.fileName = fileName;
    }

    @OneToMany(mappedBy = "project")
    java.util.List<ChangeSet> changeSets;

    public Project() {
    }

    public Project(String projectName, String fileName) {

        this.name = projectName;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<ChangeSet> getChangeSets() {
        return changeSets;
    }

    public void setChangeSets(List<ChangeSet> changeSets) {
        this.changeSets = changeSets;
    }
}
