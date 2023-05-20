package pl.martyna.bakula.coursesscraper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    boolean existsCourseEntityBySourceIdAndSourceType(int sourceId, SourceType sourceType);

    Optional <CourseEntity> findFirstBySourceIdAndSourceType(int sourceId, SourceType sourceType);

    List<CourseEntity> findCourseEntityByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTopicsIsContainingIgnoreCase(String valueForTitle, String valueForDescription, String valueForTopics);

    List<CourseEntity> findCourseEntityByLabels_Label(Label label);

    List<CourseEntity> findBySourceType(SourceType sourceType);
}
