package pl.martyna.bakula.coursesscraper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://www.udemy.com/api-2.0", name = "udemy")
interface UdemyCoursesClient {
    @GetMapping(value = "courses")
    UdemyResult getCourses(@RequestHeader ("Authorization") String authorization,
                           @RequestParam String category,
                           @RequestParam String language,
                           @RequestParam("fields[course]") String course,
                           @RequestParam("page_size") int pageSize,
                           @RequestParam("page") int page);

    @GetMapping(value = "courses/{courseId}/public-curriculum-items/")
    UdemyTopic getTopics(@PathVariable("courseId") int courseId);
}