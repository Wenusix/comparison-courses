package pl.martyna.bakula.coursesscraper;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String author;

    private String title;
    private String price;
    private String courseUrlModify;
    private String urlPhoto;

    @OneToMany(mappedBy = "course")
    private Set<CourseHistoryEntity> courseHistory;

    public CourseEntity(String author, String title, String price, String courseUrlModify, String urlPhoto) {
        this.author = author;
        this.title = title;
        this.price = price;
        this.courseUrlModify = courseUrlModify;
        this.urlPhoto = urlPhoto;
    }

    public CourseEntity() {

    }

    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getCourseUrlModify() {
        return courseUrlModify;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public Set<CourseHistoryEntity> getCourseHistory() {
        return courseHistory;
    }
}