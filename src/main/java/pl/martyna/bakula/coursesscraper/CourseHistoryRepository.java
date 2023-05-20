package pl.martyna.bakula.coursesscraper;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CourseHistoryRepository extends JpaRepository<CourseHistoryEntity, Integer> {

    @Query("select courseHistoryEntity from CourseHistoryEntity courseHistoryEntity " +
            "where courseHistoryEntity.sourceId= (:sourceId) " +
            "and courseHistoryEntity.sourceType = (:sourceType) " +
            "ORDER BY courseHistoryEntity.date DESC")
    List<CourseHistoryEntity> findCourseHistoryEntityBySourceIdAndSourceType(
            Pageable pageable, @Param("sourceId") int sourceId, @Param("sourceType") SourceType sourceType);

    List <CourseHistoryEntity> findAllBySourceId(int sourceId);

    List <CourseHistoryEntity> findAllBySourceIdAndSourceType(int sourceId, SourceType sourceType);
}
