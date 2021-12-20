package com.example.geradorprojeto.files;

import com.example.geradorprojeto.dto.ProjectDTO;

import java.io.File;
import java.nio.file.Path;

public interface FilesInterface {

    void changingContent(File baseProjectFile, Path newProjectPath, ProjectDTO project);
}
