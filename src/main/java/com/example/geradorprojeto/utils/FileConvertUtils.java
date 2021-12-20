package com.example.geradorprojeto.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class FileConvertUtils {

    public static void write(File newProjectPath, String document) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(newProjectPath));

            writer.write(document);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            log.error("Não foi possível escrever no arquivo {} no diretorio {}", document, newProjectPath, e);
        }
    }

    public String read(String newProjectPath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(newProjectPath));

            String line = reader.readLine();
            String fileContent = "";

            while (line != null) {
                fileContent = fileContent + line + System.lineSeparator();
                line = reader.readLine();
            }

            reader.close();
            return fileContent;

        } catch (IOException e) {
            log.error("Não foi possível ler o diretorio {}", newProjectPath, e);
        }

        return null;
    }
}
