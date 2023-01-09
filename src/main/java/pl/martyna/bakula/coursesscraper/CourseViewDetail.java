package pl.martyna.bakula.coursesscraper;

import java.util.List;

class CourseViewDetail {
    private final int id;
    private final SourceType sourceType;
    private final String image;
    private final String title;
    private final String author;
    private final double price;
    private final String url;
    private final String description;
    private final String topics;
    private final double courseTime;
    private final List<GroupElementView> groups;

    public CourseViewDetail(int id, SourceType sourceType, String image, String title, String author, double price, String url, String description, String topics, double courseTime, List<GroupElementView> groups) {
        this.id = id;
        this.sourceType = sourceType;
        this.image = image;
        this.title = title;
        this.author = author;
        this.price = price;
        this.url = url;
        this.description = description;
        this.topics = topics;
        this.courseTime = courseTime;
        this.groups = groups;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getTopics() {
        return topics;
    }

    public double getCourseTime() {
        return courseTime;
    }

    public List<GroupElementView> getGroups() {
        return groups;
    }

    public SourceType getSourceType() {
        return sourceType;
    }
}
class GroupElementView {
    private final int id;
    private final String url;
    private final SourceType sourceType;
    private final double price;

    public GroupElementView(int id, String url, SourceType sourceType, double price) {
        this.id = id;
        this.url = url;
        this.sourceType = sourceType;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public double getPrice() {
        return price;
    }
}