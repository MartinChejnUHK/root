package pro1;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Path inputFolder = Paths.get(System.getProperty("user.dir"), "input");
        Path outputFolder = Paths.get(System.getProperty("user.dir"), "output");

        try {
            if (!Files.exists(outputFolder)) {
                Files.createDirectories(outputFolder);
            }

            if (!Files.exists(inputFolder)) {
                System.out.println("Input folder not found.");
                return;
            }

            List<Path> csvFiles;
            try (Stream<Path> stream = Files.walk(inputFolder)) {
                csvFiles = stream
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().toLowerCase().endsWith(".csv"))
                        .collect(Collectors.toList());
            }

            for (Path csv : csvFiles) {
                handleFile(csv, outputFolder);
            }

            System.out.println("Done.");

        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }

    private static void handleFile(Path source, Path outputFolder) throws IOException {
        Path destination = outputFolder.resolve(source.getFileName());

        try (BufferedReader reader = Files.newBufferedReader(source);
             BufferedWriter writer = Files.newBufferedWriter(destination)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] segments = line.split("[=;:]", 2);
                if (segments.length < 2) continue;

                String label = segments[0].trim();
                String expression = segments[1].trim();

                Fraction result = Fraction.parse(expression);
                String output = result.toString().replace(" ", "");

                writer.write(label + "," + output);
                writer.newLine();
            }
        }
    }
}
