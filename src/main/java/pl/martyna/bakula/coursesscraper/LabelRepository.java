package pl.martyna.bakula.coursesscraper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LabelRepository extends JpaRepository<LabelEntity, Integer> {
}
