package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class UdemyTopicSubcategory {
    private final String topicSubcategory;

    @JsonCreator
    public UdemyTopicSubcategory(
            @JsonProperty ("display_name") String topicSubcategory) {
        this.topicSubcategory = topicSubcategory;
    }

    public String getTopicSubcategory() {
        return topicSubcategory;
    }
}
