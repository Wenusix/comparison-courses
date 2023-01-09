package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class UdemyAuthor {
    private final String title;

    @JsonCreator
    public UdemyAuthor(@JsonProperty ("title") String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
