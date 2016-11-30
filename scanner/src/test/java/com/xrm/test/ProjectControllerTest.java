package com.xrm.test;

import com.xrm.controllers.ProjectsController;
import com.xrm.domain.ChangeSet;
import com.xrm.domain.Project;
import com.xrm.domain.ProjectUpdate;
import com.xrm.repositories.ChangeSetRepository;
import com.xrm.repositories.ProjectRepository;
import com.xrm.repositories.ProjectUpdateRepository;
import com.xrm.services.BuildProcessingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectsController.class)
//@SpringBootTest(classes = Main.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectUpdateRepository updateRepository;
    @MockBean
    private ChangeSetRepository changeSetRepository;
    @MockBean
    private BuildProcessingService processingService;

    @Test
    public void getAllTest() throws Exception {

        ArrayList<Project> projects = new ArrayList<>();

        projects.add(new Project(1l, "Project_1", "prj1.jar"));
        projects.add(new Project(2l, "Project_2", "prj2.jar"));
        projects.add(new Project(3l, "Project_3", "prj3.jar"));
        projects.add(new Project(4l, "Project_4", "prj4.jar"));

        given(projectRepository.findAll()).willReturn(projects);

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Project_1")))
                .andExpect(content().string(containsString("Project_2")))
                .andExpect(content().string(containsString("Project_3")))
                .andExpect(content().string(containsString("Project_4")));
        verify(projectRepository).findAll();
    }

    @Test
    public void getSingle() throws Exception {
        ArrayList<ProjectUpdate> projectUpdates = new ArrayList<>();
        projectUpdates.add(new ProjectUpdate("com.xrm.Main.class", "Denis Bogomolov", "Iitial commit", "28/11/2016"));
        projectUpdates.add(new ProjectUpdate("com.xrm.Class1.class", "Denis Bogomolov", "Iitial commit", "28/11/2016"));
        projectUpdates.add(new ProjectUpdate("com.xrm.Class2.class", "Denis Bogomolov", "Iitial commit", "28/11/2016"));

        ChangeSet changeSet1 = new ChangeSet("ABCDDCBA", null, null);
        changeSet1.setProjectUpdates(projectUpdates);

        projectUpdates = new ArrayList<>();
        projectUpdates.add(new ProjectUpdate("com.xrm.Main.class", "Denis Bogomolov", "Update commit", "29/11/2016"));
        projectUpdates.add(new ProjectUpdate("com.xrm.Class1.class", "Denis Bogomolov", "Update commit", "29/11/2016"));
        projectUpdates.add(new ProjectUpdate("com.xrm.Class2.class", "Denis Bogomolov", "Update commit", "29/11/2016"));

        ChangeSet changeSet2 = new ChangeSet("DCBAABCD", null, null);
        changeSet2.setProjectUpdates(projectUpdates);

        ArrayList<ChangeSet> changeSets = new ArrayList<>();
        changeSets.add(changeSet1);
        changeSets.add(changeSet2);

        Project project = new Project(1l, "Project_1", "prj1.jar");

        project.setChangeSets(changeSets);

        given(projectRepository.findOne(1l)).willReturn(project);

        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Project_1")))
                .andExpect(content().string(containsString("ABCDDCBA")))
                .andExpect(content().string(containsString("DCBAABCD")))
                .andExpect(content().string(containsString("com.xrm.Main.class")))
                .andExpect(content().string(containsString("com.xrm.Class1.class")))
                .andExpect(content().string(containsString("com.xrm.Class2.class")));
        verify(projectRepository).findOne(1L);
    }
}
