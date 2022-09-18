package pl.martyna.bakula.coursesscraper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private CoursesRepository coursesRepository;

    public ScheduledTasks(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

//parametry Schedulara do modyfikacji
    @Scheduled(cron = "30 28,30 * * * *")
    public List<CourseEntity> coursesScraper() {
        final String urlCategory =
                "https://helion.pl/kategorie/kursy/programowanie";
        List <CourseEntity> courses = new ArrayList<>();
        try {
            final Document document = Jsoup.connect(urlCategory).get();

            for (Element element : document.select(
                    ".classPresale")) {
                if (element.select(".long-title").text().equals("")) {
                    continue;
                } else {
                    final int sourceId =
                            element.select(".price").attr("id").hashCode();
                    final String title =
                            element.select(".full-title-tooltip").text();
                    final String author =
                            element.select(".author").text();
                    String price =
                            element.select(".price").text();
                    price = price.substring(0, price.indexOf("zł")+2);
                    final String courseUrl =
                            element.select(".short-title").attr("href");
                    final String courseUrlModify = "https:" + courseUrl;
                    Document addInformation = Jsoup.connect(courseUrlModify).get();
                    String urlPhoto = addInformation.select(".cover img").attr("src");
                    CourseEntity courseEntity = new CourseEntity(sourceId, author, title, price, courseUrlModify, urlPhoto);
                    courses.add(courseEntity);

//                    final Document documentForOneCourse = Jsoup.connect(courseUrlModify).get();
//                    final String courseTime =
//                            documentForOneCourse.select(".details-box").text();
//                    System.out.println("czas kursu: " + courseTime.trim().substring(courseTime.indexOf("trwania:") + 8, courseTime.indexOf("Format:")));
//                    System.out.println("");

                }
            }
            coursesRepository.deleteAll();
            coursesRepository.saveAll(courses);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return courses;
    }
}
