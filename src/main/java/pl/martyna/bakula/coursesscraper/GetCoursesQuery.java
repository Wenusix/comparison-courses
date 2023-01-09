package pl.martyna.bakula.coursesscraper;

class GetCoursesQuery implements Query{
    private final String sort;

    public GetCoursesQuery(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }
}
