package pl.martyna.bakula.coursesscraper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCourseRepository extends JpaRepository<CourseGroupEntity, Integer> {
}
