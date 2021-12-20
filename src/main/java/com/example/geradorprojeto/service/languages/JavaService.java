package com.example.geradorprojeto.service.languages;

import com.example.geradorprojeto.domain.PathFilesEdit;
import com.example.geradorprojeto.files.JavaFile;
import com.example.geradorprojeto.service.ServiceContract;
import com.example.geradorprojeto.utils.DirectoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

@Slf4j
@Service
public class JavaService extends ServiceContract {

    private static final String SIGLA = "sigla";

    @Override
    public String getBaseProject() { return "src/main/resources/project-clone"; }

    @Override
    public String getBaseProjectName() { return "projectclone"; }

    @Override
    public String getCloneProject() { return "src/main/resources/projetoClonado"; }

    @Override
    public File createPathNameProject() {
        StringBuilder path = new StringBuilder();

        path.append(getCloneProject())
                .append("/")
                .append(projectDetails.getSigla())
                .append("-")
                .append(projectDetails.getName());

        return new File(path.toString());
    }

    @Override
    public void copyDirectory(File baseProject, Path newProjectPath) {
        Function<PathFilesEdit, Boolean> function =
                (pathFilesEdit) -> {

                    // se o arquivo que estamos percorrendo for:

                    // o diretorio principal do projeto:
                    if (pathFilesEdit.getBaseProjectFile().getName().equals(getBaseProjectName())) {
                        pathFilesEdit.setNewProjectPathCreate(
                                createDirectoryProjectName(pathFilesEdit.getNewProjectPathCreate())
                        );
                    } else if (pathFilesEdit.getBaseProjectFile().getName().equals(SIGLA)) {
                        pathFilesEdit.setNewProjectPathCreate(
                                createSiglaDirectory(pathFilesEdit.getNewProjectPathCreate())
                        );
                    } else {
                        createFile(
                                pathFilesEdit.getBaseProjectPath(),
                                pathFilesEdit.getNewProjectPathCreate().toString()
                        );
                    }

                    filesToChangeContent(
                            pathFilesEdit.getBaseProjectFile(), pathFilesEdit.getNewProjectPathCreate()
                    );

                    return true;
                };

        DirectoryUtils directory = new DirectoryUtils();
        directory.copy(baseProject, newProjectPath, function);
    }

    private Path createDirectoryProjectName(Path newProjectPathCreate) {
        try {
            // caso o nome principal do projeto contenha um hifen, deve criar a lista de diretorio, um dentro do outro
            if (projectDetails.getName().contains("-")) {
                String[] directories = projectDetails.getName().split("-");

                for (String directory: directories) {
                    newProjectPathCreate = newProjectPathCreate.resolveSibling(directory);
                    Files.createDirectory(newProjectPathCreate);

                    if (isLastDirectory(directories, directory)) {
                        newProjectPathCreate = Paths.get(newProjectPathCreate.toString().concat("/" + directory));
                    }
                }

                return newProjectPathCreate;
            }

            return Files.createDirectory(newProjectPathCreate.resolveSibling(projectDetails.getName()));
        } catch (IOException e) {
            log.error("Não foi possível criar o diretorio {}", newProjectPathCreate, e);
        }
        return null;
    }

    private boolean isLastDirectory(String[] directories, String directory) {
        return directories[directories.length - 1] != directory;
    }

    private Path createSiglaDirectory(Path newProjectPathCreate) {
        try {
            return Files.createDirectory(newProjectPathCreate.resolveSibling(projectDetails.getSigla()));
        } catch (IOException e) {
            log.error("Não foi possível criar o diretorio {} de sigla {}", newProjectPathCreate, projectDetails.getSigla(), e);
        }
        return null;
    }

    private void filesToChangeContent(File baseProjectFile, Path newProjectPathCreate) {
        new JavaFile().changingContent(baseProjectFile, newProjectPathCreate, projectDetails);
    }
}
