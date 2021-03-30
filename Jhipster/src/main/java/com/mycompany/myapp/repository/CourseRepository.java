package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Course entity.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(
        value = "select distinct course from Course course left join fetch course.users left join fetch course.instructor",
        countQuery = "select count(distinct course) from Course course"
    )
    Page<Course> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        value = "select * from course where id in (select course_id from rel_course__user rcu where rcu.user_id=:id)",
        nativeQuery = true
    )
    Optional<List<Course>> findAllCoursesByUserID(@Param("id") Long id);

    @Query(value = "select * from course where instructor_id=:id", nativeQuery = true)
    Optional<List<Course>> findAllCoursesByInstructorID(@Param("id") Long id);

    @Query(value = "select count(*) from REL_COURSE__USER where course_id = :id", nativeQuery = true)
    Integer getAttendeesNumByCourseID(@Param("id") Long id);

    @Query("select distinct course from Course course left join fetch course.users left join fetch course.instructor")
    List<Course> findAllWithEagerRelationships();

    @Query("select course from Course course")
    Optional<List<Course>> findCourseByType();

    @Query("select course from Course course left join fetch course.users left join fetch course.instructor where course.id =:id")
    Optional<Course> findOneWithEagerRelationships(@Param("id") Long id);
}
