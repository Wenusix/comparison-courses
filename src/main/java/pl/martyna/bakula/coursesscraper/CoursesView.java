package pl.martyna.bakula.coursesscraper;

class CoursesView {
    private final int id;
    private final String urlPhoto;
    private final String title;
    private final String author;
    private final String price;
    private final String courseUrlModify;

    public CoursesView(int id, String urlPhoto, String title, String author, String price, String courseUrlModify) {
        this.id = id;
        this.urlPhoto = urlPhoto;
        this.title = title;
        this.author = author;
        this.price = price;
        this.courseUrlModify = courseUrlModify;
    }

    public int getId() {
        return id;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }

    public String getCourseUrlModify() {
        return courseUrlModify;
    }
}
