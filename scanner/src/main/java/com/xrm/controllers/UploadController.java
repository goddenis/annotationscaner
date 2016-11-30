package com.xrm.controllers;

import com.xrm.domain.Project;
import com.xrm.repositories.ProjectRepository;
import com.xrm.services.BuildProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {

    @Autowired
    BuildProcessingService processingService;

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/files")
    public String getUploadPage(Model model){
        return "upload";
    }

    @GetMapping("/files/{id}")
    public String getUploadPageForProject(@PathVariable("id") Long id ,Model model){
        model.addAttribute("projectId",  id);

        return "upload";
    }


    @PostMapping("/files")
    public String handleUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){


        Project process = processingService.process(file);

        redirectAttributes.addFlashAttribute("message"," File uploaded");
        redirectAttributes.addFlashAttribute("project",process);

        return "redirect:/files/uploaded";

    }

    @PostMapping("/files/{id}")
    public String handleUploadWithProject(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id , RedirectAttributes redirectAttributes){
        Project project = processingService.process(id, file);

        redirectAttributes.addFlashAttribute("message"," File uploaded");
        redirectAttributes.addFlashAttribute("project",project);

        return "redirect:/files/uploaded";

    }

    @GetMapping("/files/uploaded")
    public String uploaded(Model model){
        return "uploaded";
    }
//
//    @ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
//    public String handle(FileUploadBase.FileSizeLimitExceededException exc){
//        return "redirect:/error";
//    }
}
