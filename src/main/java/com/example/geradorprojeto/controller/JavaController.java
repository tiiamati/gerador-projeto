package com.example.geradorprojeto.controller;

import com.example.geradorprojeto.domain.PomDetails;
import com.example.geradorprojeto.domain.Project;
import com.example.geradorprojeto.service.languages.JavaService;
import com.example.geradorprojeto.utils.StringFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

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
        project.setNameCamelCase(StringFormatUtils.getCamelCaseNameWithHypenJoined(name));
        project.setNameWithDot(name.replace("-", "."));

        PomDetails pomDetails = new PomDetails();
        pomDetails.setGroupId("org.projectlombok");
        pomDetails.setArtifactId("lombok");
        pomDetails.setVersion("1.18.20");
        pomDetails.setScope("provided");

        project.setPomDetails(Collections.singletonList(pomDetails));

        Resource resource = javaService.createProject(project);

//        javaService.deleteCloneProject();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
