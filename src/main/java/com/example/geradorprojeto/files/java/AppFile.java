package com.example.geradorprojeto.files.java;

import com.example.geradorprojeto.domain.Project;
import com.example.geradorprojeto.utils.FileConvertUtils;

import java.nio.file.Path;

public class AppFile {

    public void changingContent(Path newProjectPath, Project project) {

        StringBuilder newString = new StringBuilder();

        newString
                .append("com.")
                .append(project.getName().replace("-", "."));

        FileConvertUtils fileConvertUtils = new FileConvertUtils();

        String newContent = fileConvertUtils.read(newProjectPath.toString()).replace("replacePackage", newString);

        fileConvertUtils.write(newProjectPath.toFile(), newContent);
    }
}
