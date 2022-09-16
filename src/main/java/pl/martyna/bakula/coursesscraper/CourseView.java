package pl.martyna.bakula.coursesscraper;

class CourseView {
    private final String urlPhoto;
    private final String title;
    private final String author;
    private final String price;
    private final String courseUrlModify;

    public CourseView(String urlPhoto, String title, String author, String price, String courseUrlModify) {
        this.urlPhoto = urlPhoto;
        this.title = title;
        this.author = author;
        this.price = price;
        this.courseUrlModify = courseUrlModify;
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
