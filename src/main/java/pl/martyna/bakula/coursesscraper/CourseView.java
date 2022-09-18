package pl.martyna.bakula.coursesscraper;

class CourseView {
    private final int id;
    private final String urlPhoto;
    private final String title;
    private final String author;
    private final String price;

    public CourseView(int id, String urlPhoto, String title, String author, String price) {
        this.id = id;
        this.urlPhoto = urlPhoto;
        this.title = title;
        this.author = author;
        this.price = price;
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
}
