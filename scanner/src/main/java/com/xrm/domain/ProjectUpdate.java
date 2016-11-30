package com.xrm.domain;

import javax.persistence.*;

@Entity
public class ProjectUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String className;
    private String author;
    private String description;
    private String date;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ChangeSet changeSet;

    public ProjectUpdate() {
    }

    public ProjectUpdate(String className,String author, String description, String date) {
        this.className = className;
        this.author = author;
        this.description = description;
        this.date = date;
    }

    public ProjectUpdate(String className, String author, String description, String date, ChangeSet changeSet) {
        this.className = className;
        this.author = author;
        this.description = description;
        this.date = date;
        this.changeSet = changeSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ChangeSet getChangeSet() {
        return changeSet;
    }

    public void setChangeSet(ChangeSet changeSet) {
        this.changeSet = changeSet;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
