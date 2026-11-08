package ua.lpnu.kzp.setup;

import java.nio.file.Path;

/**
 * Зберігає параметри запуску програми.
 */
public final class ProgramArguments
{
    private final Path inputPath;
    private final Path outputPath;
    private final boolean help;
    private final boolean version;

    public ProgramArguments(
            Path inputPath,
            Path outputPath,
            boolean help,
            boolean version
    )
    {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.help = help;
        this.version = version;
    }

    public Path getInputPath()
    {
        return inputPath;
    }

    public Path getOutputPath()
    {
        return outputPath;
    }

    public boolean isHelp()
    {
        return help;
    }

    public boolean isVersion()
    {
        return version;
    }
}