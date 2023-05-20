package pl.martyna.bakula.coursesscraper;

import javax.persistence.*;

@Entity
@Table(name = "labels")
class LabelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Label label;
    private String labelSource;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    public LabelEntity(Label label, String labelSource, CourseEntity course) {
        this.label = label;
        this.labelSource = labelSource;
        this.course = course;
    }

    public LabelEntity() {

    }

    public Integer getId() {
        return id;
    }

    public Label getLabel() {
        return label;
    }

    public String getLabelSource() {
        return labelSource;
    }

    public CourseEntity getCourse() {
        return course;
    }
}
