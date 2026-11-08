package ua.lpnu.kzp.setup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Керує основним процесом обробки даних.
 */
public final class TrainingController
{
    private TrainingController()
    {
    }

    public static void run(ProgramArguments arguments) throws IOException
    {
        List<String> lines = Files.readAllLines(
                arguments.getInputPath(),
                StandardCharsets.UTF_8
        );

        String report = HelloApplication.buildReport(lines);

        System.out.println();
        System.out.print(report);

        Files.writeString(
                arguments.getOutputPath(),
                report,
                StandardCharsets.UTF_8
        );

        System.out.printf(
                "%nЗвіт збережено у: %s%n",
                arguments.getOutputPath()
        );
    }
}