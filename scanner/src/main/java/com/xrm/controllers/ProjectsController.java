package com.xrm.controllers;

import com.xrm.domain.Project;
import com.xrm.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProjectsController {

    @Autowired
    ProjectRepository projectRepository;

    @RequestMapping(path = "/projects", method = RequestMethod.GET)
    public String getProjects(Model model){

        model.addAttribute("projects",projectRepository.findAll());

        return "projects\\all";
    }

    @RequestMapping(path = "/projects/{id}", method = RequestMethod.GET)
    public String getProject(@PathVariable("id") long id, Model model){
        Project one = projectRepository.findOne(id);
        model.addAttribute("project", one);
        return "projects\\single";
    }

}
