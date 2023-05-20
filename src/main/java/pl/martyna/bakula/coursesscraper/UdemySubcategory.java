package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class UdemySubcategory {
    private final String subcategoryTitle;

    @JsonCreator
    public UdemySubcategory(
            @JsonProperty ("title_cleaned") String subcategoryTitle) {
        this.subcategoryTitle = subcategoryTitle;
    }

    public String getSubcategoryTitle() {
        return subcategoryTitle;
    }
}
