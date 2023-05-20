package pl.martyna.bakula.coursesscraper;

class GetCourseByLabelQuery implements Query{
    private final String value;
    private final String sort;

    public GetCourseByLabelQuery(String value, String sort) {
        this.value = value;
        this.sort = sort;
    }

    public String getValue() {
        return value;
    }

    public String getSort() {
        return sort;
    }
}
