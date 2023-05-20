package pl.martyna.bakula.coursesscraper;

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

import java.util.ArrayList;
import java.util.List;

@Order(2)
@Component
public class StrefaKursowTaskExecutor implements SchedulerExecution{

    private final CourseRepository courseRepository;
    private final CourseHistoryRepository courseHistoryRepository;
    private final CourseGroupingTask courseGroupingTask;
    private final Logger logger = LogManager.getLogger(StrefaKursowTaskExecutor.class);

    public StrefaKursowTaskExecutor(CourseRepository courseRepository, CourseHistoryRepository courseHistoryRepository, CourseGroupingTask courseGroupingTask) {
        this.courseRepository = courseRepository;
        this.courseHistoryRepository = courseHistoryRepository;
        this.courseGroupingTask = courseGroupingTask;
    }

    @Override
    @Transactional
    public void execute() {
        logger.info("Rozpoczęto pozyskiwanie danych ze Strefy Kursów");

        int page = 1;
        boolean nextPage = true;
        List<CourseEntity> courses = new ArrayList<>();
        List<CourseHistoryEntity> coursesHistory = new ArrayList<>();

        while(nextPage) {
            try {
                final String urlCategory = "https://strefakursow.pl/kursy/programowanie/" + page +
                        ".html?sort_by=priority_sort&direction=desc&limit=20&filter%5Bdisplay_mode%5D=box";
                page += 1;
                final Document document = Jsoup.connect(urlCategory).get();

                for (Element element : document.select(
                        ".b-product-list .b-product-box.desktop")) {
                    final String urlTemp = element.select("a").attr("href");
                    final String url = "https://strefakursow.pl/" + urlTemp;
                    final String title = element.select(".name.desktop").text();
                    double price = Double.parseDouble(
                            element.select(".price.desktop .new.desktop")
                                    .text().replace("zł",""));
                    String image = element.select(".lazy-img__product-list")
                                    .attr("data-src");
                    logger.info(title);
                    final Document documentDetails = Jsoup.connect(url).get();
                    String time = documentDetails.select(".icon.desktop").get(1).text();
                    double courseTime = Double.parseDouble(
                            time.replace(",", ".")
                                    .replace("h","")
                                    .substring(0, time.indexOf(" ")));
                    int sourceId = Integer.parseInt(
                            documentDetails.select(".hidden").attr("content"));

                    String authorWithProfession = documentDetails.select(
                            ".b-course-standard__top-author-name").text();
                    String profession = documentDetails.select(".b-course-standard__top-author-title").text();
                    String author = authorWithProfession.substring(0, authorWithProfession.indexOf(profession));

                    String description = String.valueOf(documentDetails.select(
                            ".b-product-description__filling"))
                            .replace("src=\"“/redesign/assets/images/1x1.png”\"", "")
                            .replace("data-src=\"", "src=\"")
                            .replace("src=\"", "src=\"https://strefakursow.pl/");

                    String topics = String.valueOf(documentDetails.select(".c-contents"));

                    if (!courseRepository.existsCourseEntityBySourceIdAndSourceType(
                            sourceId, SourceType.STREFA_KURSOW)) {
                        CourseEntity courseEntity = new CourseEntity(
                                sourceId, SourceType.STREFA_KURSOW, author, title, price,
                                url, image, courseTime, description, topics);

                        Elements labelElement = documentDetails.select(".c-tag-navigation__content-product a");
                        for (Element value : labelElement) {
                            String label = value.text();
                            final Label labelByKey = Label.getLabelByKey(label);
                            LabelEntity labelEntity = new LabelEntity(labelByKey, label, courseEntity);
                            courseEntity.getLabels().add(labelEntity);
                        }
                        courses.add(courseEntity);
                        courseGroupingTask.findSimilaritiesByCourseEntity(courseEntity);
                    } else {
                        CourseEntity foundCourse = courseRepository.findFirstBySourceIdAndSourceType(
                                sourceId, SourceType.STREFA_KURSOW)
                                .orElseThrow(() -> new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "Nie odnaleziono kursu."));
                        foundCourse.setAuthor(author);
                        foundCourse.setTitle(title);
                        foundCourse.setPrice(price);
                        foundCourse.setImage(image);
                        foundCourse.setUrl(url);
                    }

                    Pageable pageable = PageRequest.of(0, 1);
                    courseHistoryRepository.findCourseHistoryEntityBySourceIdAndSourceType(
                            pageable, sourceId, SourceType.STREFA_KURSOW)
                            .stream().findFirst().ifPresentOrElse(
                                    v -> {
                                        if (v.getPrice() != price) {
                                            CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity(
                                                    sourceId, SourceType.STREFA_KURSOW, price);
                                            coursesHistory.add(courseHistoryEntity);
                                        }
                                    },
                                    () -> {
                                        CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity(
                                                sourceId, SourceType.STREFA_KURSOW, price);
                                        coursesHistory.add(courseHistoryEntity);
                                    }
                            );
                }
                courseRepository.saveAll(courses);
                courseHistoryRepository.saveAll(coursesHistory);

            } catch (Exception ex) {
                nextPage = false;
                ex.printStackTrace();
            }
        }
    }
}

