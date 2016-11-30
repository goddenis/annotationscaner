package com.xrm.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class ChangeSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileHash;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "changeSet")
    private Project project;

    @OneToMany(mappedBy = "changeSet")
    private List<ProjectUpdate> projectUpdates;

    public ChangeSet() {
    }

    public ChangeSet(String fileHash, Date date, Project project) {
        this.fileHash = fileHash;
        this.date = date;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectUpdate> getProjectUpdates() {
        return projectUpdates;
    }

    public void setProjectUpdates(List<ProjectUpdate> projectUpdates) {
        this.projectUpdates = projectUpdates;
    }
}
