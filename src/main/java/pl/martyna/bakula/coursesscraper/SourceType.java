package pl.martyna.bakula.coursesscraper;

enum SourceType {
    UDEMY(1, "UDEMY"),
    HELION(3, "HELION"),
    STREFA_KURSOW(2, "STREFA KURSÓW"),
    ;

    private final int priority;
    private final String name;

    SourceType(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }
}
