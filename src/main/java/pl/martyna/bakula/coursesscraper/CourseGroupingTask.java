package pl.martyna.bakula.coursesscraper;

import org.apache.commons.text.similarity.*;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
class CourseGroupingTask {
    private final CourseRepository courseRepository;
    private final JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();

    CourseGroupingTask(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    void findSimilaritiesByCourseEntity(CourseEntity newCourse) {
        SourceType sourceType = newCourse.getSourceType();
        final List<SourceType> otherSourceTypes = Arrays.stream(SourceType.values())
                .filter(v -> v != sourceType)
                .collect(Collectors.toUnmodifiableList());

        for (SourceType otherSourceType : otherSourceTypes) {
            for (CourseEntity courseEntity : courseRepository.findBySourceType(otherSourceType)) {
                if(compare(newCourse, courseEntity)){
                    if(courseEntity.getGroup() != null ) {
                        CourseGroupEntity group = courseEntity.getGroup();
                        group.getCourses().add(newCourse);
                    }else if(newCourse.getGroup() != null){
                        CourseGroupEntity group = newCourse.getGroup();
                        group.getCourses().add(courseEntity);
                    }else{
                        CourseGroupEntity courseGroupEntity = new CourseGroupEntity();
                        courseGroupEntity.getCourses().add(newCourse);
                        courseGroupEntity.getCourses().add(courseEntity);
                        newCourse.setGroup(courseGroupEntity);
                        courseEntity.setGroup(courseGroupEntity);
                    }

                }
            }
        }
    }

    boolean compare(CourseEntity firstCourse, CourseEntity secondCourse){
        if(firstCourse.getTitle().equalsIgnoreCase(secondCourse.getTitle())){
            return true;
        }
        final String firstText = Jsoup.parse(firstCourse.getDescription()).text();
        final String secondText = Jsoup.parse(secondCourse.getDescription()).text();

        String firstTopics = Jsoup.parse(firstCourse.getTopics()).text().replaceAll("[0-9.:]", "").trim().replaceAll("\\s{2,}", " ");
        String secondTopics = Jsoup.parse(secondCourse.getTopics()).text().replaceAll("[0-9.:]", "").trim().replaceAll("\\s{2,}", " ");
        final Double jaroWinklerSimilarityApply = jaroWinklerSimilarity.apply(firstText, secondText);

        int min = Math.min(Math.min(firstTopics.length(), secondTopics.length()), 800);

        firstTopics = firstTopics.substring(0, min);
        secondTopics = secondTopics.substring(0, min);

        boolean titleIsSimilar = jaroWinklerSimilarity.apply(firstCourse.getTitle(), secondCourse.getTitle()) > 0.8;
        boolean descriptionSimilar = jaroWinklerSimilarityApply > 0.75;
        boolean descriptionVerySimilar = jaroWinklerSimilarityApply > 0.9;
        boolean topicsSame = firstTopics.equalsIgnoreCase(secondTopics);
        boolean topicsVerySimilar = jaroWinklerSimilarity.apply(firstTopics, secondTopics) > 0.9;

        int points = 0;

        if(titleIsSimilar){
            points += 40;
        }

        if(descriptionVerySimilar){
            points += 80;
        } else if(descriptionSimilar) {
            points += 50;
        }
        if(topicsVerySimilar){
            points += 80;
        }else if(topicsSame){
            points += 100;
        }
        return points >= 100;
    }
}
