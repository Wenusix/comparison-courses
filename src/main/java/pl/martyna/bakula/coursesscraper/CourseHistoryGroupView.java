package pl.martyna.bakula.coursesscraper;

import java.util.List;

class CourseHistoryGroupView {

    private final SourceType sourceType;
    private final List<CourseHistoryView> elements;

    CourseHistoryGroupView(SourceType sourceType, List<CourseHistoryView> elements) {
        this.sourceType = sourceType;
        this.elements = elements;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public List<CourseHistoryView> getElements() {
        return elements;
    }
}
