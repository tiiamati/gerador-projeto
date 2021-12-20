package com.example.geradorprojeto.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.nio.file.Path;

@Getter
@Setter
public class PathFilesEdit {
    private Path newProjectPathCreate;
    private Path baseProjectPath;
    private File baseProjectFile;
}
