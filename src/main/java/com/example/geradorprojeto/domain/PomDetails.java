package com.example.geradorprojeto.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PomDetails {
    private String groupId;
    private String artifactId;
    private String version;
    private String scope;
}
