package com.example.geradorprojeto.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringFormatUtils {

    public static String getCamelCaseNameWithHypenJoined(String name) {
        return Arrays.stream(name.split("-"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining());
    }
}
