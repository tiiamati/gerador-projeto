package com.example.geradorprojeto.files.java;

import com.example.geradorprojeto.domain.Project;
import com.example.geradorprojeto.utils.FileConvertUtils;
import org.apache.commons.text.WordUtils;

import java.nio.file.Path;

public class ReadmeFile {

    public void changingContent(Path newProjectPath, Project project) {

        FileConvertUtils fileConvertUtils = new FileConvertUtils();

        String newContent = fileConvertUtils.read(newProjectPath.toString())
                .replace("SIGLA_SUBSTITUIR", project.getSigla())
                .replace("NOME_PROJETO_SUBSTITUIR", getProjectName(project.getName()))
                .replace("DESCRICAO_SUBSTITUIR", project.getDescription());


        fileConvertUtils.write(newProjectPath.toFile(), newContent);
    }

    private CharSequence getProjectName(String projectName) {
        return WordUtils.capitalizeFully(projectName.replace("-", " "));
    }
}
