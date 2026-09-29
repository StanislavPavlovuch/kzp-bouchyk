package ua.lpnu.kzp.setup;

/** Мінімальна консольна програма для перевірки Maven. */
public final class HelloApplication {

    private HelloApplication() {
    }

    /**
     * Запускає програму.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        if (args.length > 0 && "--version".equals(args[0])) {
    System.out.printf("maven-actions-hello 0.1.0%n");
    return;
        }
        System.out.printf("Hello from Maven%n");
    }
}