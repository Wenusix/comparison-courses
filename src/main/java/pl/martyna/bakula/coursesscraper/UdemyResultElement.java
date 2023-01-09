package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class UdemyResultElement {
    private final int id;
    private final String title;
    private final String url;
    private final String description;
    private final String price;
    private final String image;
    private final String courseTime;
    private boolean practiceTest;
    private final List<UdemyAuthor> udemyAuthors;
    private final UdemyPromotionalPrice udemyPromotionalPrice;
    private final UdemySubcategory udemySubcategory;
    private final List<UdemyLabel> udemyLabels;

    @JsonCreator
    public UdemyResultElement(
            @JsonProperty("id") int id,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url,
            @JsonProperty("description") String description,
            @JsonProperty("price") String price,
            @JsonProperty("image_480x270") String image,
            @JsonProperty("content_info_short") String courseTime,
            @JsonProperty("is_practice_test_course") boolean practiceTest,
            @JsonProperty("visible_instructors") List<UdemyAuthor> udemyAuthors,
            @JsonProperty("discount") UdemyPromotionalPrice udemyPromotionalPrice,
            @JsonProperty("primary_subcategory") UdemySubcategory udemySubcategory,
            @JsonProperty("course_has_labels") List<UdemyLabel> udemyLabels) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description = description;
        this.price = price;
        this.image = image;
        this.courseTime = courseTime;
        this.practiceTest = practiceTest;
        this.udemyAuthors = udemyAuthors;
        this.udemyPromotionalPrice = udemyPromotionalPrice;
        this.udemySubcategory = udemySubcategory;
        this.udemyLabels = udemyLabels;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }


    public String getImage() {
        return image;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public UdemyPromotionalPrice getUdemyPromotionalPrice() {
        return udemyPromotionalPrice;
    }

    public String getPrice() {
        return price;
    }

    public boolean isPracticeTest() {
        return practiceTest;
    }

    public List<UdemyAuthor> getUdemyAuthors() {
        return udemyAuthors;
    }

    public UdemySubcategory getUdemySubcategory() {
        return udemySubcategory;
    }

    public List<UdemyLabel> getUdemyLabels() {
        return udemyLabels;
    }
}
