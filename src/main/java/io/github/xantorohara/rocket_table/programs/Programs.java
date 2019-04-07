package io.github.xantorohara.rocket_table.programs;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Programs {


    public static List<Program> loadPrograms() throws IOException {
        log.info("Loading programs");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("programs.json"))) {
            Program[] data = new Gson().fromJson(reader, Program[].class);
            log.info("Loaded [{}] programs", data.length);
            return Arrays.asList(data);
        }
    }

    public static void exec(Program program) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(program.getCmd().split(" "))
                .inheritIO();

        Process process = processBuilder.start();
        if (program.getResult() != null) {
            process.waitFor();
        }
    }
}
