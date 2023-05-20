package pl.martyna.bakula.coursesscraper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = "scheduler.enable=false")
@SpringBootTest
class UdemyTest {

    @Autowired
    private UdemyCoursesClient udemyCoursesClient;
    @Value("${udemy.secret.key}")
    private String udemySecretKey;
    int page = 1;

    @Test
    public void should_get_elements_from_api_then_check_correctness_of_them() {

        final UdemyResult result = udemyCoursesClient.getCourses(
                udemySecretKey, "IT & Software", "pl",
                "id,title,url,description,price,image_480x270,visible_instructors," +
                        "primary_subcategory,course_has_labels,content_info_short,discount," +
                        "is_practice_test_course", 100, page);
        assertThat(result.getElements())
                .doesNotContainNull()
                .allSatisfy(v -> assertThat(v.getPrice()).isNotNull().isNotBlank())
                .allSatisfy(v -> assertThat(v.getTitle()).isNotNull().isNotBlank())
                .allSatisfy(v -> assertThat(v.getUdemyAuthors()).isNotNull().isNotEmpty().doesNotContainNull())
                .allSatisfy(v -> assertThat(v.getImage()).isNotNull().isNotBlank())
                .allSatisfy(v -> assertThat(v.getUrl()).isNotNull().isNotBlank())
                .allSatisfy(v -> assertThat(v.getDescription()).isNotNull().isNotBlank())
                .allSatisfy(v -> assertThat(v.getUdemyLabels()).isNotNull().isNotEmpty().doesNotContainNull())
                .allSatisfy(v -> assertThat(v.getCourseTime()).isNotNull().isNotBlank());
    }
}
