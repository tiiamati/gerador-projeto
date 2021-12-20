package com.example.geradorprojeto.files;

import com.example.geradorprojeto.domain.Project;
import com.example.geradorprojeto.files.java.AppFile;
import com.example.geradorprojeto.files.java.ApplicationPropertiesFile;
import com.example.geradorprojeto.files.java.PomFile;
import com.example.geradorprojeto.files.java.ReadmeFile;

import java.io.File;
import java.nio.file.Path;

public class JavaFile implements FilesInterface {

    private static final String POM = "pom.xml";
    private static final String README = "README.md";
    private static final String APP_FILE = "ProjectCloneApplication.java";
    private static final String APPLICATION_PROPERTIES = "application.properties";

    @Override
    public void changingContent(File baseProjectFile, Path newProjectPath, Project project) {

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
