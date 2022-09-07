package pl.martyna.bakula.coursesscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CoursesController {

    @CrossOrigin
    @GetMapping
    public List<CourseView> coursesScraper() {
        final String urlCategory =
                "https://helion.pl/kategorie/kursy/programowanie";
        List <CourseView> courses = new ArrayList<>();
        try {
            final Document document = Jsoup.connect(urlCategory).get();

            for (Element element : document.select(
                    ".classPresale")) {
                if (element.select(".long-title").text().equals("")) {
                    continue;
                } else {
//                    final String urlPhoto =
//                            element.select(".lazy").attr("src");
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
                    //urlPhoto = urlPhoto.substring(urlPhoto.indexOf("src="+5), urlPhoto.indexOf("width"+7));
                    CourseView courseView = new CourseView(urlPhoto, title, author, price, courseUrl, courseUrlModify);
                    courses.add(courseView);

//                    final Document documentForOneCourse = Jsoup.connect(courseUrlModify).get();
//                    final String courseTime =
//                            documentForOneCourse.select(".details-box").text();
//                    System.out.println("czas kursu: " + courseTime.trim().substring(courseTime.indexOf("trwania:") + 8, courseTime.indexOf("Format:")));
//                    System.out.println("");
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return courses;
    }
}
