package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class UdemyResult {
    private final int count;
    private final List<UdemyResultElement> elements;

    @JsonCreator
    public UdemyResult(
            @JsonProperty ("count") int count,
            @JsonProperty("results") List<UdemyResultElement> elements) {
        this.count = count;
        this.elements = elements;
    }

    public int getCount() {
        return count;
    }

    public List<UdemyResultElement> getElements() {
        return elements;
    }
}
