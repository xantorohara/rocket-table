package io.github.xantorohara.rocket_table.programs;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Programs {

    @SneakyThrows
    public static List<Program> loadProcessors() {
        log.info("Loading processors");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("programs.json"))) {
            Program[] data = new Gson().fromJson(reader, Program[].class);
            log.info("Loaded [{}] processors", data.length);
            return Arrays.asList(data);
//        } catch (IOException e) {
//            throw new MetricsException(format(MSG_CAN_NOT_READ, filepath), e);
//        } catch (JsonParseException e) {
//            throw new MetricsException(format(MSG_CAN_NOT_PARSE, filepath), e);
        }
    }

    @SneakyThrows
    public static void exec(Program program) {
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(program.getCmd().split(" "))
                .inheritIO();

        Process process = processBuilder.start();
        process.waitFor();
    }

}
