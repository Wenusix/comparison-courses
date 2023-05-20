package pl.martyna.bakula.coursesscraper;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int sourceId;
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;
    private String author;
    private String title;
    private double price;
    private String url;
    private String image;
    private double courseTime;
    @Column(length = 50000)
    private String description;
    @Column(length = 50000)
    private String topics;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private CourseGroupEntity group;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<LabelEntity> labels = new HashSet<>();

    public CourseEntity(int sourceId, SourceType sourceType, String author, String title, double price, String url, String image, double courseTime, String description, String topics) {
        this.sourceId = sourceId;
        this.sourceType = sourceType;
        this.author = author;
        this.title = title;
        this.price = price;
        this.url = url;
        this.image = image;
        this.courseTime = courseTime;
        this.description = description;
        this.topics = topics;
    }

    public CourseEntity() {

    }


    public void setGroup(CourseGroupEntity group) {
        this.group = group;
    }

    public CourseGroupEntity getGroup() {
        return group;
    }

    public Integer getId() {
        return id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCourseTime() {
        return courseTime;
    }

    public String getTopics() {
        return topics;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public Set<LabelEntity> getLabels() {
        return labels;
    }
}
