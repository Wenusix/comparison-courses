package pl.martyna.bakula.coursesscraper;

class CourseHistoryView {
    private final int id;
    private final int sourceId;
    private final double price;
    private final SourceType sourceType;
    private final String date;

    public CourseHistoryView(int id, int sourceId, double price, SourceType sourceType, String date) {
        this.id = id;
        this.sourceId = sourceId;
        this.price = price;
        this.sourceType = sourceType;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public double getPrice() {
        return price;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public String getDate() {
        return date;
    }
}