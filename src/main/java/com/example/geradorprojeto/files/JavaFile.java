package com.example.geradorprojeto.files;

import com.example.geradorprojeto.dto.ProjectDTO;
import com.example.geradorprojeto.files.java.AppFile;
import com.example.geradorprojeto.files.java.ApplicationPropertiesFile;
import com.example.geradorprojeto.files.java.PomFile;
import com.example.geradorprojeto.files.java.ReadmeFile;
import com.example.geradorprojeto.utils.StringFormatUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JavaFile implements FilesInterface {

    private static final String POM = "pom.xml";
    private static final String README = "README.md";
    private static final String APP_FILE = "ProjectCloneApplication.java";
    private static final String APPLICATION_PROPERTIES = "application.properties";

    @Override
    public void changingContent(File baseProjectFile, Path newProjectPath, ProjectDTO project) {

        if (baseProjectFile.getName().equals(POM)) {
            new PomFile().changingContent(newProjectPath, project);
            return;
        }

        if (baseProjectFile.getName().equals(README)) {
            new ReadmeFile().changingContent(newProjectPath, project);
            return;
        }

        if (baseProjectFile.getName().equals(APP_FILE)) {
            new AppFile().changingContent(newProjectPath, project);
            return;
        }

        if (baseProjectFile.getName().equals(APPLICATION_PROPERTIES)) {
            new ApplicationPropertiesFile().changingContent(newProjectPath, project);
            return;
        }
    }
}
