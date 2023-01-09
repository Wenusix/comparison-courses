package pl.martyna.bakula.coursesscraper;

enum SourceType {
    UDEMY(1),
    HELION(3),
    STREFA_KURSOW(2),
    ;

    private final int priority;

    SourceType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
