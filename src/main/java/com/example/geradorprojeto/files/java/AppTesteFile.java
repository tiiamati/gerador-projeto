package com.example.geradorprojeto.files.java;

import com.example.geradorprojeto.domain.Project;
import com.example.geradorprojeto.utils.FileConvertUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class AppTesteFile {

    public void changingContent(Path newProjectPath, Project project) {

        StringBuilder packageName = new StringBuilder();
        packageName
                .append("com.")
                .append(project.getNameWithDot());

        String fileName = project.getNameCamelCase().concat("ApplicationTests");

        FileConvertUtils fileConvertUtils = new FileConvertUtils();

        String newContent = fileConvertUtils
                .read(newProjectPath.toString())
                .replace("replacePackage", packageName)
                .replace("replaceName", fileName);

        fileConvertUtils.write(newProjectPath.toFile(), newContent);

        try {
            Files.move(newProjectPath, newProjectPath.resolveSibling(fileName.concat(".java")));
        } catch (Exception exception) {
            log.error("Não foi possível renomear o arquivo: {}", newProjectPath);
        }
    }
}
