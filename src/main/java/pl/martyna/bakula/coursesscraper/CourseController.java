package pl.martyna.bakula.coursesscraper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@CrossOrigin
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseHistoryRepository courseHistoryRepository;

    public CourseController(CourseRepository courseRepository, CourseHistoryRepository courseHistoryRepository) {
        this.courseRepository = courseRepository;
        this.courseHistoryRepository = courseHistoryRepository;
    }

    @GetMapping
    public List<CourseView> findAllCourse(GetCoursesQuery query) {
        final List<CourseEntity> coursesList = courseRepository.findAll();
        final List<CourseView> coursesToReturn = coursesList
                .stream()
                .filter(v -> v.getGroup() == null)
                .map(v -> new CourseView(v.getId(), v.getImage(), v.getTitle(), v.getAuthor(), v.getPrice(), v.getUrl(), v.getDescription(), v.getTopics(), v.getCourseTime()))
                .collect(Collectors.toList());
        Map<Integer, CourseView> courses = new HashMap<>();
        for(CourseEntity courseEntity : coursesList){
            final CourseGroupEntity group = courseEntity.getGroup();
            if(group != null){
                final CourseView courseView = group.getCourses()
                        .stream()
                        .max(Comparator.comparingInt(v -> v.getSourceType().getPriority()))
                        .map(v -> new CourseView(v.getId(), v.getImage(), v.getTitle(), v.getAuthor(), v.getPrice(), v.getUrl(), v.getDescription(), v.getTopics(), v.getCourseTime()))
                        .orElseThrow();
                courses.putIfAbsent(courseView.getId(), courseView);
            }
        }
        coursesToReturn.addAll(courses.values());
        return coursesToReturn
                .stream()
                .sorted(getComparatorBySort(query.getSort()))
                .collect(Collectors.toUnmodifiableList());
    }


    private List<CourseView> getCourses(List<CourseEntity> entities, String sort){
        final List<CourseView> coursesToReturn = entities
                .stream()
                .filter(v -> v.getGroup() == null)
                .map(v -> new CourseView(v.getId(), v.getImage(), v.getTitle(), v.getAuthor(), v.getPrice(), v.getUrl(), v.getDescription(), v.getTopics(), v.getCourseTime()))
                .collect(Collectors.toList());
        Map<Integer, CourseView> courses = new HashMap<>();
        for(CourseEntity courseEntity : entities){
            final CourseGroupEntity group = courseEntity.getGroup();
            if(group != null){
                final CourseView courseView = group.getCourses()
                        .stream()
                        .max(Comparator.comparingInt(v -> v.getSourceType().getPriority()))
                        .map(v -> new CourseView(v.getId(), v.getImage(), v.getTitle(), v.getAuthor(), v.getPrice(), v.getUrl(), v.getDescription(), v.getTopics(), v.getCourseTime()))
                        .orElseThrow();
                courses.putIfAbsent(courseView.getId(), courseView);
            }
        }
        coursesToReturn.addAll(courses.values());
        return coursesToReturn
                .stream()
                .sorted(getComparatorBySort(sort))
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("{id}")
    public CourseViewDetail findCourseById(GetCourseQuery query) {
        CourseEntity foundCourse = courseRepository.findById(query.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie odnaleziono kursu"));
        final CourseGroupEntity group = foundCourse.getGroup();
        List<GroupElementView> groupElements = Collections.emptyList();
        if(group != null){
            groupElements = group.getCourses().stream()
                    .filter(v -> !Objects.equals(v.getId(), foundCourse.getId()))
                    .map(v -> new GroupElementView(v.getId(), v.getUrl(), v.getSourceType(), v.getPrice()))
                    .collect(Collectors.toUnmodifiableList());

        }
        return new CourseViewDetail(foundCourse.getId(), foundCourse.getSourceType(), foundCourse.getImage(), foundCourse.getTitle(), foundCourse.getAuthor(), foundCourse.getPrice(), foundCourse.getUrl(), foundCourse.getDescription(), foundCourse.getTopics(), foundCourse.getCourseTime(), groupElements);
    }
    @GetMapping("history")
    public List<CourseHistoryGroupView> findAllCourseHistory(GetCourseHistoryQuery query) {
        CourseEntity foundCourse = courseRepository.findById(query.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie odnaleziono kursu"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<CourseHistoryGroupView> groupViewList;
        if(foundCourse.getGroup() == null) {
            List<CourseHistoryEntity> foundCoursesHistory = courseHistoryRepository.findAllBySourceIdAndSourceType(foundCourse.getSourceId(), foundCourse.getSourceType());
            List<CourseHistoryView> elements = foundCoursesHistory
                    .stream()
                    .map(v -> new CourseHistoryView(v.getId(), v.getSourceId(), v.getPrice(), v.getSourceType(), v.getDate().format(formatter)))
                    .collect(Collectors.toUnmodifiableList());
            groupViewList = Collections.singletonList(new CourseHistoryGroupView(foundCourse.getSourceType(), elements));
        }else{
            final Set<CourseEntity> courses = foundCourse.getGroup().getCourses();
            groupViewList = new ArrayList<>(courses.size());
            for(CourseEntity course : courses){
                List<CourseHistoryEntity> foundCoursesHistory = courseHistoryRepository.findAllBySourceIdAndSourceType(course.getSourceId(), course.getSourceType());
                List<CourseHistoryView> elements = foundCoursesHistory
                        .stream()
                        .map(v -> new CourseHistoryView(v.getId(), v.getSourceId(), v.getPrice(), v.getSourceType(), v.getDate().format(formatter)))
                        .collect(Collectors.toUnmodifiableList());
                groupViewList.add(new CourseHistoryGroupView(course.getSourceType(), elements));
            }
        }

        return groupViewList;
    }
    @GetMapping("search")
    public List<CourseView> findCourseBySearch(GetValueQuery query) {
        List<CourseEntity> foundCourses = courseRepository.findCourseEntityByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTopicsIsContainingIgnoreCase(query.getValue(),query.getValue(),query.getValue());
        return getCourses(foundCourses, query.getSort());
    }

    @GetMapping("labelName")
    public List<String> findLabelsList() {
        final List<String> labelList = new ArrayList<>();

        for (Label element : Label.values()) {
            if(!element.equals(Label.OTHER)){
            final String text = element.getText();
            labelList.add(text);
            }
        }
        return labelList;
    }

    @GetMapping("label")
    public List<CourseView> findCoursesByLabel(GetCourseByLabelQuery query){
        final Label labelByText = Label.getLabelByText(query.getValue());

        List<CourseEntity> coursesListByLabel = courseRepository.findCourseEntityByLabels_Label(labelByText);
        return getCourses(coursesListByLabel, query.getSort());
    }

    private static Comparator<CourseView> getComparatorBySort(String sortType) {
        return switch (sortType) {
            case "price" -> Comparator.comparing(CourseView::getPrice);
            case "price-desc" -> Comparator.comparing(CourseView::getPrice).reversed();
            case "time" -> Comparator.comparing(CourseView::getCourseTime);
            case "time-desc" -> Comparator.comparing(CourseView::getCourseTime).reversed();
            default -> Comparator.comparing(CourseView::getTitle);
        };
    }
}
