package pl.martyna.bakula.coursesscraper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    private CoursesRepository coursesRepository;

    public CourseController(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @CrossOrigin
    @GetMapping
    public List<CourseView> coursesScraper() {
        final List<CourseEntity> coursesList = coursesRepository.findAll();

        return coursesList
                .stream()
                .map(v -> new CourseView(v.getUrlPhoto(), v.getTitle(), v.getAuthor(), v.getPrice(), v.getCourseUrlModify()))
                .collect(Collectors.toUnmodifiableList());
    }
}
