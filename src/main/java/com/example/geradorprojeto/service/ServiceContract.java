package com.example.geradorprojeto.service;

import com.example.geradorprojeto.domain.PathFilesEdit;
import com.example.geradorprojeto.dto.ProjectDTO;
import com.example.geradorprojeto.utils.DirectoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

@Slf4j
public abstract class ServiceContract {

    protected ProjectDTO projectDetails;

    protected abstract File createPathNameProject();

    protected abstract String getBaseProject();

    protected abstract String getBaseProjectName();

    protected abstract String getCloneProject();

    public Resource createProject(ProjectDTO project) {
        this.projectDetails = project;

        File newProjectDirectory = createPathNameProject();
        File baseProject = new File(getBaseProject());

        if (newProjectDirectory.mkdirs()) {
            copyDirectory(baseProject, newProjectDirectory.toPath());
            try {
                return new UrlResource(getZip(newProjectDirectory.toString()));
            } catch (MalformedURLException e) {
                log.error("Não foi possível criar o zip", e);
            }
        }

        return null;
    }

    public void deleteCloneProject() {
        File project = new File(getCloneProject());

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(
                () -> {
                    try {
                        Thread.sleep(2000);
                        FileUtils.deleteDirectory(project);
                    } catch (IOException | InterruptedException e) {
                        log.error("Não foi possível deletar o diretorio {}", project.getName(), e);
                    }
                }
        );
    }

    protected URI getZip(String newProjectDirectory) {
        DirectoryUtils directory = new DirectoryUtils();
        return Paths.get(directory.zip(newProjectDirectory)).toUri();
    }

    protected void copyDirectory(File baseProject, Path newProjectPath) {
        Function<PathFilesEdit, Boolean> function =
                (pathFilesEdit) -> {
                    createFile(
                            pathFilesEdit.getBaseProjectPath(),
                            pathFilesEdit.getNewProjectPathCreate().toString()
                    );
                    return true;
                };
        DirectoryUtils directoryUtils = new DirectoryUtils();
        directoryUtils.copy(baseProject, newProjectPath, function);
    }

    protected void createFile(Path baseProjectPath, String newProjectPathCreate) {
        try {
            Files.copy(baseProjectPath, getRealNamePathNewProject(newProjectPathCreate));
        } catch (IOException e) {
            log.error("Não foi possível copiar os arquivos na criação do projeto {}", projectDetails.getName(), e);
        }
    }

    private Path getRealNamePathNewProject(String newProjectPathCreate) {
        if (getBaseProjectName() != null) {
            return Paths.get(newProjectPathCreate.replace(getBaseProjectName(), projectDetails.getName()));
        }
        return Paths.get(newProjectPathCreate);
    }
}

