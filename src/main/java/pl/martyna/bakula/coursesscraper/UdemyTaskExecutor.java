package pl.martyna.bakula.coursesscraper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Order(1)
@Component
class UdemyTaskExecutor implements SchedulerExecution{
    @Value("${udemy.secret.key}")
    private String udemySecretKey;
    private final CourseRepository courseRepository;
    private final CourseHistoryRepository courseHistoryRepository;
    private final UdemyCoursesClient udemyCoursesClient;

    private final CourseGroupingTask courseGroupingTask;

    private final Logger logger = LogManager.getLogger(UdemyTaskExecutor.class);

    public UdemyTaskExecutor(CourseRepository courseRepository, CourseHistoryRepository courseHistoryRepository, UdemyCoursesClient udemyCoursesClient, CourseGroupingTask courseGroupingTask) {
        this.courseRepository = courseRepository;
        this.courseHistoryRepository = courseHistoryRepository;
        this.udemyCoursesClient = udemyCoursesClient;
        this.courseGroupingTask = courseGroupingTask;
    }

    @Override
    @Transactional
    public void execute() {
        logger.info("Rozpoczęto pozyskiwanie danych z Udemy");

        Map<Integer, CourseEntity> courses = new HashMap<>();
        Map<Integer, CourseHistoryEntity> coursesHistory = new HashMap<>();
        boolean nextPage = true;
        int page = 1;

        while (nextPage) {

            try {
                final UdemyResult udemyResult = udemyCoursesClient.getCourses(
                        udemySecretKey, "IT & Software", "pl",
                        "id,title,url,description,price,image_480x270,visible_instructors," +
                                "primary_subcategory,course_has_labels,content_info_short,discount," +
                                "is_practice_test_course", 100, page);
                page += 1;

                for (UdemyResultElement element : udemyResult.getElements()) {
                    final UdemySubcategory udemySubcategory = element.getUdemySubcategory();
                    if(udemySubcategory == null) {
                        continue;
                    }
                    final String subcategory = udemySubcategory.getSubcategoryTitle();
                    if (subcategory == null || !subcategory.equals("programming-languages")) {
                        continue;
                    }

                    if(element.isPracticeTest()){
                        continue;
                    }

                    final int sourceId = element.getId();
                    final String title = element.getTitle();
                    final String url = "https://udemy.com" + element.getUrl();
                    final String image = element.getImage();
                    final String description = element.getDescription();
                    final String time = element.getCourseTime();
                    double courseTime = Double.parseDouble(time.substring(0, time.indexOf(" ")));
                    double price;
                    if(element.getUdemyPromotionalPrice() == null){
                        price = Double.parseDouble(element.getPrice().replace("zł", "").replace("Free", "0"));
                    } else {
                        price = element.getUdemyPromotionalPrice().getUdemyPromotionalPriceDetails().getPrice();
                    }
                    logger.info(title);
                    final List<UdemyAuthor> elementsAuthor = element.getUdemyAuthors();
                    StringBuilder authorTemp = new StringBuilder();
                    for (UdemyAuthor elementAuthor : elementsAuthor) {
                        authorTemp.append(elementAuthor.getTitle()).append(", ");
                    }
                    String author = String.valueOf(authorTemp).substring(0, authorTemp.length() - 2);

                    int topicNumber = 0;
                    StringBuilder topics = new StringBuilder();

                    final UdemyTopic udemyTopic = udemyCoursesClient.getTopics(sourceId);
                    for (UdemyTopicElement topicElement : udemyTopic.getTopicElement()) {
                        topicNumber += 1;
                        topics.append(topicNumber).append(".").append(topicElement.getTitle()).append(" <br> ");
                    }

                    if (!courseRepository.existsCourseEntityBySourceIdAndSourceType(sourceId, SourceType.UDEMY)) {
                        if (!courses.containsKey(sourceId)) {
                            CourseEntity courseEntity = new CourseEntity(sourceId, SourceType.UDEMY, author, title, price, url, image, courseTime, description, topics.toString());
                            courses.put(sourceId, courseEntity);


                            for (UdemyLabel label : element.getUdemyLabels()) {
                                final String udemyCourseLabel = label.getUdemyCourseLabel().getNameLabel();
                                final Label labelByKey = Label.getLabelByKey(udemyCourseLabel);
                                LabelEntity labelEntity = new LabelEntity(labelByKey, udemyCourseLabel, courseEntity);
                                courseEntity.getLabels().add(labelEntity);
                            }
                            courseGroupingTask.findSimilaritiesByCourseEntity(courseEntity);
                            courseRepository.save(courseEntity);
                        }
                    } else {
                        CourseEntity foundCourse = courseRepository.findFirstBySourceIdAndSourceType(sourceId, SourceType.UDEMY)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie odnaleziono kursu."));
                        foundCourse.setAuthor(author);
                        foundCourse.setTitle(title);
                        foundCourse.setPrice(price);
                        foundCourse.setImage(image);
                        foundCourse.setUrl(url);
                    }

                    Pageable pageable = PageRequest.of(0, 1);
                    courseHistoryRepository.findCourseHistoryEntityBySourceIdAndSourceType(pageable, sourceId, SourceType.UDEMY)
                            .stream().findFirst().ifPresentOrElse(
                                    v -> {
                                        if (v.getPrice() != price) {
                                            CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity(sourceId, SourceType.UDEMY, price);
                                            coursesHistory.put(sourceId, courseHistoryEntity);
                                        }
                                    },
                                    () -> {
                                        CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity(sourceId, SourceType.UDEMY, price);
                                        coursesHistory.put(sourceId, courseHistoryEntity);
                                    }
                            );
                }
                courseHistoryRepository.saveAll(coursesHistory.values());
            } catch (Exception e) {
                nextPage = false;
            }
        }
    }
}

