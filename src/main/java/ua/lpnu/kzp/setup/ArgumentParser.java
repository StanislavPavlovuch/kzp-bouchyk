package ua.lpnu.kzp.setup;

import java.nio.file.Path;

/**
 * Розбирає аргументи командного рядка.
 */
public final class ArgumentParser
{
    private ArgumentParser()
    {
    }

    public static ProgramArguments parse(String[] args)
    {
        Path inputPath = Path.of("data/input.csv");
        Path outputPath = Path.of("data/report.txt");
        boolean help = false;
        boolean version = false;

        for (int i = 0; i < args.length; i++)
        {
            switch (args[i])
            {
                case "--help" ->
                {
                    help = true;
                }

                case "--version" ->
                {
                    version = true;
                }

                case "--input" ->
                {
                    if (i + 1 >= args.length)
                    {
                        throw new IllegalArgumentException(
                                "Після --input потрібно вказати шлях до файла."
                        );
                    }

                    inputPath = Path.of(args[++i]);
                }

                case "--output" ->
                {
                    if (i + 1 >= args.length)
                    {
                        throw new IllegalArgumentException(
                                "Після --output потрібно вказати шлях до файла."
                        );
                    }

                    outputPath = Path.of(args[++i]);
                }

                default ->
                {
                    throw new IllegalArgumentException(
                            "Невідомий аргумент: " + args[i]
                    );
                }
            }
        }

        return new ProgramArguments(
                inputPath,
                outputPath,
                help,
                version
        );
    }
}