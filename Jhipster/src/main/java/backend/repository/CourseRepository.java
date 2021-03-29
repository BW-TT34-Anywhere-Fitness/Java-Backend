package backend.repository;

import backend.domain.Authority;
import backend.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


}
