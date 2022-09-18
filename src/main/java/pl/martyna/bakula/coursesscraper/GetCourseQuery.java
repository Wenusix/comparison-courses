package pl.martyna.bakula.coursesscraper;

import javax.validation.constraints.NotNull;

class GetCourseQuery implements Query{
    @NotNull(message = "Nie odnaleziono kursu")
    private final Integer courseId;

    public GetCourseQuery(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseId() {
        return courseId;
    }
}
