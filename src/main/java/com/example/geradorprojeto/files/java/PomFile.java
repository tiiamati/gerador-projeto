package com.example.geradorprojeto.files.java;

import com.example.geradorprojeto.domain.Project;
import com.example.geradorprojeto.utils.FileConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class PomFile {

    private static final String POM_NAME = "name";
    private static final String POM_ARTIFACT_ID = "artifactId";
    private static final String POM_DESCRIPTION = "description";

    public void changingContent(Path newProjectPath, Project project) {
        Document document = Jsoup.parse(readFileToString(newProjectPath), "", Parser.xmlParser());
        document.outputSettings().prettyPrint(false);

        log.debug("Alterando conteudo do arquivo POM");
        changingPomValue(document, project);

        FileConvertUtils fileConvertUtils = new FileConvertUtils();

        fileConvertUtils.write(newProjectPath.toFile(), document.toString());
    }

    public String readFileToString(Path newProjectPath) {
        try {
            return FileUtils.readFileToString(newProjectPath.toFile());
        } catch (IOException e) {
            log.error("Não foi possível convertero arquivo em string {}", newProjectPath, e);
        }
        return null;
    }

    public void changingPomValue(Document document, Project project) {

        StringBuilder artifactId = new StringBuilder();

        artifactId
                .append(project.getSigla())
                .append(".api.")
                .append(project.getName().replace("-", "."));

        document.getElementsByTag(POM_NAME).get(0).appendText(project.getName());
        document.getElementsByTag(POM_ARTIFACT_ID).get(1).appendText(artifactId.toString());
        document.getElementsByTag(POM_DESCRIPTION).get(0).appendText(project.getDescription());
    }
}
