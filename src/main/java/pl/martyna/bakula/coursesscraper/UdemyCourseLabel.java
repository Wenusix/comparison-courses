package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class UdemyCourseLabel {
    private final String nameLabel;

    @JsonCreator
    public UdemyCourseLabel(
            @JsonProperty("display_name") String nameLabel) {
        this.nameLabel = nameLabel;
    }

    public String getNameLabel() {
        return nameLabel;
    }
}
