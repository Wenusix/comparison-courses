package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class UdemyTopic {
    private final int count;
    private final List<UdemyTopicElement> topicElement;

    @JsonCreator
    public UdemyTopic(
            @JsonProperty("count") int count,
            @JsonProperty("results") List<UdemyTopicElement> topicElement) {
        this.count = count;
        this.topicElement = topicElement;
    }

    public int getCount() {
        return count;
    }

    public List<UdemyTopicElement> getTopicElement() {
        return topicElement;
    }
}
