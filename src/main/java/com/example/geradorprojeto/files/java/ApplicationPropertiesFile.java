package com.example.geradorprojeto.files.java;

import com.example.geradorprojeto.dto.ProjectDTO;
import com.example.geradorprojeto.utils.FileConvertUtils;

import java.nio.file.Path;

public class ApplicationPropertiesFile {

    public void changingContent(Path newProjectPath, ProjectDTO project) {

        FileConvertUtils fileConvertUtils = new FileConvertUtils();

        String newContent = fileConvertUtils.read(newProjectPath.toString());

        if (project.getSigla().equals("ev9")) {
            newContent.replace("api.connector.ims.user=", "api.connector.ims.user=ERSS")
                    .replace("api.connector.ims.token=", "api.connector.ims.token=HHHE");
        }

        newContent = newContent.replace("application.name=", "application.name=" + project.getName());

        fileConvertUtils.write(newProjectPath.toFile(), newContent);
    }
}
