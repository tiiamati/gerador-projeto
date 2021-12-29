package com.example.geradorprojeto.utils;

import com.example.geradorprojeto.domain.PathFilesEdit;
import lombok.extern.slf4j.Slf4j;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

@Slf4j
public class DirectoryUtils {

    public void copy(File baseProject, Path newProjectPath, Function<PathFilesEdit, Boolean> function) {

        for (File baseProjectFile: baseProject.listFiles()) {

            PathFilesEdit pathFilesEdit = new PathFilesEdit();

            pathFilesEdit.setBaseProjectPath(baseProjectFile.toPath());
            pathFilesEdit.setBaseProjectFile(baseProjectFile);

            pathFilesEdit.setNewProjectPathCreate(
                    newProjectPath.resolve(pathFilesEdit.getBaseProjectPath().getFileName())
            );

            function.apply(pathFilesEdit);

            if (baseProjectFile.listFiles() != null) {
                copy(baseProjectFile, pathFilesEdit.getNewProjectPathCreate(), function);
            }
        }
    }

    public String zip(String newProjectDirectory) {
        String zipDirectory = newProjectDirectory.concat(".zip");
        ZipUtil.pack(new File(newProjectDirectory), new File(zipDirectory));
        return zipDirectory;
    }
}
