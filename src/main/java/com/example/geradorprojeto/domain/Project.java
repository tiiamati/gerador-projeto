package com.example.geradorprojeto.domain;

import com.example.geradorprojeto.utils.StringFormatUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Project {
    private static final String SIGLA = "sigla";

    private String name;
    private String description;
    private String sigla;
    private String nameCamelCase;
    private String nameWithDot;
    private List<PomDetails> pomDetails;
}
