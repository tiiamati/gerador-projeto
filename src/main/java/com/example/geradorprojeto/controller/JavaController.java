package com.example.geradorprojeto.controller;

import com.example.geradorprojeto.domain.Project;
import com.example.geradorprojeto.service.languages.JavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/java")
public class JavaController {

    @Autowired
    private JavaService javaService;

    @GetMapping()
    public String index() {
        return "java";
    }

    @PostMapping
    public ResponseEntity<Resource> add(
            @RequestParam("sigla") String sigla,
            @RequestParam("description") String description,
            @RequestParam("name") String name) {

        Project project = new Project();

        project.setSigla(sigla);
        project.setDescription(description);
        project.setName(name);

        Resource resource = javaService.createProject(project);

        javaService.deleteCloneProject();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
