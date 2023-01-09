package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class UdemyLabel {
    private final UdemyCourseLabel udemyCourseLabel;

    @JsonCreator
    public UdemyLabel(
            @JsonProperty ("label") UdemyCourseLabel udemyCourseLabel) {
        this.udemyCourseLabel = udemyCourseLabel;
    }

    public UdemyCourseLabel getUdemyCourseLabel() {
        return udemyCourseLabel;
    }
}
