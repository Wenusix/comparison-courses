package pl.martyna.bakula.coursesscraper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Order(3)
@Component
public class HelionTaskExecutor implements SchedulerExecution {

    private final CourseRepository courseRepository;
    private final CourseHistoryRepository courseHistoryRepository;
    private final CourseGroupingTask courseGroupingTask;

    private final Logger logger = LogManager.getLogger(HelionTaskExecutor.class);

    public HelionTaskExecutor(CourseRepository courseRepository, CourseHistoryRepository courseHistoryRepository, CourseGroupingTask courseGroupingTask) {
        this.courseRepository = courseRepository;
        this.courseHistoryRepository = courseHistoryRepository;
        this.courseGroupingTask = courseGroupingTask;
    }

    @Override
    @Transactional
    public void execute() {
        logger.info("Rozpoczęto pozyskiwanie danych z Heliona");

        boolean nextPage = true;
        int page = 1;
        List<CourseEntity> courses = new ArrayList<>();
        List<CourseHistoryEntity> coursesHistory = new ArrayList<>();

        while (nextPage) {
            try {
                final String urlCategory =
                        "https://helion.pl/kategorie/kursy/programowanie/" + page;
                final Document document = Jsoup.connect(urlCategory).get();
                page += 1;
                nextPage = !document.select(".classPresale .long-title").isEmpty();

                for (Element element : document.select(
                        ".classPresale")) {
                    final int sourceId =
                            element.select(".price").attr("id").hashCode();
                    final String title =
                            element.select(".full-title-tooltip").text();
                    final String author =
                            element.select(".author").text();
                    String priceText =
                            element.select(".price").text();
                    double price = Double.parseDouble(priceText.substring(0, priceText.indexOf(".") + 3));
                    final String courseUrl =
                            element.select(".short-title").attr("href");
                    final String courseUrlModify = "https:" + courseUrl;
                    Document addInformation = Jsoup.connect(courseUrlModify).get();
                    String urlPhoto = addInformation.select(".cover img").attr("src");
                    logger.info(title);
                    final Document courseDetails = Jsoup.connect(courseUrlModify).get();
                    String description = String.valueOf(courseDetails.select(".center-body-center"));
                    String topics = String.valueOf(courseDetails.select(".clip-list"));

                    String time = courseDetails.select(".movie-description .container").text();
                    String timeToConvert = time.substring(time.indexOf("trwania:") + 9, time.indexOf(" Ocena") - 3).replace(":", ".");
                    double courseTime = MathUtil.convertTimeToDecimal(timeToConvert);

                    if (!courseRepository.existsCourseEntityBySourceIdAndSourceType(sourceId, SourceType.HELION)) {
                        CourseEntity courseEntity = new CourseEntity(sourceId, SourceType.HELION, author, title, price, courseUrlModify, urlPhoto, courseTime, description, topics);

                        Elements labelElements = courseDetails.select(".details-box ul span");
                        for (int i = 1; i < labelElements.size(); i += 2) {
                            String label = labelElements.get(i).text();
                            final Label labelByKey = Label.getLabelByKey(label);
                            LabelEntity labelEntity = new LabelEntity(labelByKey, label, courseEntity);
                            courseEntity.getLabels().add(labelEntity);
                        }
                        courseGroupingTask.findSimilaritiesByCourseEntity(courseEntity);
                        courses.add(courseEntity);
                    } else {
                        CourseEntity foundCourse = courseRepository.findFirstBySourceIdAndSourceType(sourceId, SourceType.HELION)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie odnaleziono kursu."));
                        foundCourse.setAuthor(author);
                        foundCourse.setTitle(title);
                        foundCourse.setPrice(price);
                        foundCourse.setImage(urlPhoto);
                        foundCourse.setUrl(courseUrlModify);
                    }

                    Pageable pageable = PageRequest.of(0, 1);
                    courseHistoryRepository.findCourseHistoryEntityBySourceIdAndSourceType(pageable, sourceId, SourceType.HELION)
                            .stream().findFirst().ifPresentOrElse(
                                    v -> {
                                        if (v.getPrice() != price) {
                                            CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity(sourceId, SourceType.HELION, price);
                                            coursesHistory.add(courseHistoryEntity);
                                        }
                                    },
                                    () -> {
                                        CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity(sourceId, SourceType.HELION, price);
                                        coursesHistory.add(courseHistoryEntity);
                                    }
                            );
                }

                courseRepository.saveAll(courses);
                courseHistoryRepository.saveAll(coursesHistory);

            } catch (Exception ex) {
                ex.printStackTrace();
                nextPage = false;
            }
        }
    }
}
