package pl.martyna.bakula.coursesscraper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class CourseController {

    private CoursesRepository coursesRepository;

    public CourseController(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @GetMapping
    public List<CoursesView> coursesScraper() {
        final List<CourseEntity> coursesList = coursesRepository.findAll();

        return coursesList
                .stream()
                .map(v -> new CoursesView(v.getId(), v.getUrlPhoto(), v.getTitle(), v.getAuthor(), v.getPrice(), v.getCourseUrlModify()))
                .collect(Collectors.toUnmodifiableList());
    }
    @GetMapping("element")
    public CourseView courseScaper(GetCourseQuery query) {
        CourseEntity courseEntity = coursesRepository.findById(query.getCourseId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie odnalezniono kursu"));

        return new CourseView(courseEntity.getId(), courseEntity.getUrlPhoto(), courseEntity.getTitle(), courseEntity.getAuthor(), courseEntity.getPrice());
    }
}
