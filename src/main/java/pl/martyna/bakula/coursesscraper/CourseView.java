package pl.martyna.bakula.coursesscraper;

class CourseView {
    private final int id;
    private final String image;
    private final String title;
    private final String author;
    private final double price;
    private final String url;
    private final String description;
    private final String topics;
    private final double courseTime;

    public CourseView(int id, String image, String title, String author, double price, String url, String description, String topics, double courseTime) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.price = price;
        this.url = url;
        this.description = description;
        this.topics = topics;
        this.courseTime = courseTime;
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
}
