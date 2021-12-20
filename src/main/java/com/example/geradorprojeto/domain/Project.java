package com.example.geradorprojeto.domain;

import com.example.geradorprojeto.utils.StringFormatUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {
    private static final String SIGLA = "sigla";

    private String name;
    private String description;
    private String sigla;
    private String nameCamelCase;

    private void setNameCamelCase(String value) {
        nameCamelCase = StringFormatUtils.getCamelCaseNameWithHypenJoined(value);
    }
}
