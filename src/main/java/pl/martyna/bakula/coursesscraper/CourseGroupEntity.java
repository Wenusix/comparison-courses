package pl.martyna.bakula.coursesscraper;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coursesGroups")
class CourseGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "group")
    private Set<CourseEntity> courses = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }
}
