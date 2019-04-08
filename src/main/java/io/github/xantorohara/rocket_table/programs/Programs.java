package io.github.xantorohara.rocket_table.programs;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Programs {

    private static final String PROGRAMS_JSON = "programs.json";

    private static Path getSharedConfig() {
        String jarLocation = Programs.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return Paths.get(new File(jarLocation).getParent(), PROGRAMS_JSON);
    }

    private static List<Program> loadPrograms(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Program[] data = new Gson().fromJson(reader, Program[].class);
            log.info("Loaded [{}] programs from [{}]", data.length, path);
            return Arrays.asList(data);
        }
    }

    public static List<Program> loadPrograms() throws IOException {
        log.info("Loading programs");

        List<Program> programs = new ArrayList<>();

        Path sharedConfig = getSharedConfig();
        if (sharedConfig.toFile().isFile()) {
            programs.addAll(loadPrograms(sharedConfig));
        }

        Path localConfig = Paths.get(PROGRAMS_JSON);

        if (localConfig.toFile().isFile() && !Files.isSameFile(localConfig, sharedConfig)) {
            programs.addAll(loadPrograms(localConfig));
        }

        return programs;
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
