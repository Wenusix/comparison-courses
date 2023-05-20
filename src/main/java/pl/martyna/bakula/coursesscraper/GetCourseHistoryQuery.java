package pl.martyna.bakula.coursesscraper;

import javax.validation.constraints.NotNull;

class GetCourseHistoryQuery implements Query{
    @NotNull(message = "Nie odnaleziono kursu")
    private final Integer id;

    public GetCourseHistoryQuery(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
