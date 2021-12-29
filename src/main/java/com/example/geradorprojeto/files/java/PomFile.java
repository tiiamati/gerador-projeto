package com.example.geradorprojeto.files.java;

import com.example.geradorprojeto.domain.PomDetails;
import com.example.geradorprojeto.domain.Project;
import com.example.geradorprojeto.utils.FileConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class PomFile {

    private static final String POM_NAME = "name";
    private static final String POM_ARTIFACT_ID = "artifactId";
    private static final String POM_DESCRIPTION = "description";
    private static final String POM_GROUP_ID = "groupId";

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

        StringBuilder sigla = new StringBuilder();

        sigla
                .append("com")
                .append(".")
                .append(project.getSigla());

        document.getElementsByTag(POM_NAME).get(0).appendText(project.getName());
        document.getElementsByTag(POM_ARTIFACT_ID).get(1).appendText(project.getNameWithDot());
        document.getElementsByTag(POM_DESCRIPTION).get(0).appendText(project.getDescription());
        document.getElementsByTag(POM_GROUP_ID).get(1).appendText(sigla.toString());

        if (!project.getPomDetails().isEmpty()) {
            Elements dependencies = document.getElementsByTag("dependency");

            for (PomDetails pom: project.getPomDetails()) {
                dependencies.add(getDependencyElement(pom));
//                dependencies.append(getDependencyElement(pom));
            }
        }

    }

    private Element getDependencyElement(PomDetails pomDetails) {
        Element dependency = new Element("dependency");

        if (pomDetails.getGroupId() != null) {
            dependency.appendChild(getGroupId(dependency, pomDetails));
        }

        if (pomDetails.getArtifactId() != null) {
            dependency.appendChild(getArtifactId(dependency, pomDetails));
        }

        if (pomDetails.getVersion() != null) {
            dependency.appendChild(getVersion(dependency, pomDetails));
        }

        if (pomDetails.getScope() != null) {
            dependency.appendChild(getScope(dependency, pomDetails));
        }

        return dependency;
    }

    private Element getArtifactId(Element dependency, PomDetails pomDetails) {
        return new Element("artifactId")
                .append(pomDetails.getArtifactId());
    }

    private Element getGroupId(Element dependency, PomDetails pomDetails) {
        return new Element("groupId")
                .append(pomDetails.getGroupId());
    }

    private Element getVersion(Element dependency, PomDetails pomDetails) {
        return new Element("version")
                .append(pomDetails.getVersion());
    }

    private Element getScope(Element dependency, PomDetails pomDetails) {
        return new Element("scope")
                .append(pomDetails.getScope());
    }
}
