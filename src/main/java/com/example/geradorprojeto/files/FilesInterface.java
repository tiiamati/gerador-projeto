package com.example.geradorprojeto.files;

import com.example.geradorprojeto.domain.Project;

import java.io.File;
import java.nio.file.Path;

public interface FilesInterface {

    void changingContent(File baseProjectFile, Path newProjectPath, Project project);
}
