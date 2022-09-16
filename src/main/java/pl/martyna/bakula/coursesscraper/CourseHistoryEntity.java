package pl.martyna.bakula.coursesscraper;

import javax.persistence.*;

@Entity
@Table(name = "historyCourses")
public class CourseHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String date;
    private String price;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;
}
