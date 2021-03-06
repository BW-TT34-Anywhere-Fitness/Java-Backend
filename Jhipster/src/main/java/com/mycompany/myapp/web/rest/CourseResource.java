package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.UserextraRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Course}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CourseResource {

    private final Logger log = LoggerFactory.getLogger(CourseResource.class);

    private static final String ENTITY_NAME = "course";

    @Autowired
    UserService userService;

    @Autowired
    UserextraRepository userextraRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseRepository courseRepository;

    public CourseResource(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * {@code POST  /courses} : Create a new course.
     *
     * @param course the course to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new course, or with status {@code 400 (Bad Request)} if the course has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) throws URISyntaxException {
        log.debug("REST request to save Course : {}", course);
        if (course.getId() != null) {
            throw new BadRequestAlertException("A new course cannot already have an ID", ENTITY_NAME, "idexists");
        }

        var currentuser = userService.getUserWithAuthorities().orElseThrow();

        if (!userextraRepository.findOneById(currentuser.getId()).orElseThrow().getAccountype().equals("instructor")) {
            throw new BadRequestAlertException("Only instructor can add a course", ENTITY_NAME, "notinstructor");
        }

        course.setInstructor(userService.getUserWithAuthorities().get());
        Course result = courseRepository.save(course);
        return ResponseEntity
            .created(new URI("/api/courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /courses/:id} : Updates an existing course.
     *
     * @param id     the id of the course to save.
     * @param course the course to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated course,
     * or with status {@code 400 (Bad Request)} if the course is not valid,
     * or with status {@code 500 (Internal Server Error)} if the course couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable(value = "id", required = false) final Long id, @RequestBody Course course)
        throws URISyntaxException {
        log.debug("REST request to update Course : {}, {}", id, course);
        if (course.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, course.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        var currentuser = userService.getUserWithAuthorities().orElseThrow();
        if (!userextraRepository.findOneById(currentuser.getId()).orElseThrow().getAccountype().equals("instructor")) {
            throw new BadRequestAlertException("Only instructor can edit a course", ENTITY_NAME, "notinstructor");
        }
        if (!course.getInstructor().getId().equals(currentuser.getId())) {
            throw new BadRequestAlertException("Only owner can edit this course", ENTITY_NAME, "notowner");
        }

        Course result = courseRepository.save(course);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, course.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /courses/:id} : Partial updates given fields of an existing course, field will ignore if it is null
     *
     * @param id     the id of the course to save.
     * @param course the course to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated course,
     * or with status {@code 400 (Bad Request)} if the course is not valid,
     * or with status {@code 404 (Not Found)} if the course is not found,
     * or with status {@code 500 (Internal Server Error)} if the course couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/courses/{id}", consumes = { "application/merge-patch+json", "application/json" })
    public ResponseEntity<Course> partialUpdateCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Course course
    ) throws URISyntaxException {
        log.debug("REST request to partial update Course partially : {}, {}", id, course);
        //        if (course.getId() == null) {
        //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        //        }
        //        if (!Objects.equals(id, course.getId())) {
        //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        //        }

        if (!courseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        var currentuser = userService.getUserWithAuthorities().orElseThrow();
        if (!userextraRepository.findOneById(currentuser.getId()).orElseThrow().getAccountype().equals("instructor")) {
            throw new BadRequestAlertException("Only instructor can edit a course", ENTITY_NAME, "notinstructor");
        }
        if (!courseRepository.findById(id).get().getInstructor().getId().equals(currentuser.getId())) {
            throw new BadRequestAlertException("Only owner can edit this course", ENTITY_NAME, "notowner");
        }

        Optional<Course> result = courseRepository
            .findById(id)
            .map(
                existingCourse -> {
                    if (course.getName() != null) {
                        existingCourse.setName(course.getName());
                    }
                    if (course.getType() != null) {
                        existingCourse.setType(course.getType());
                    }
                    if (course.getStarttime() != null) {
                        existingCourse.setStarttime(course.getStarttime());
                    }
                    if (course.getDuration() != null) {
                        existingCourse.setDuration(course.getDuration());
                    }
                    if (course.getIntensity() != null) {
                        existingCourse.setIntensity(course.getIntensity());
                    }
                    if (course.getLocation() != null) {
                        existingCourse.setLocation(course.getLocation());
                    }
                    if (course.getAttenndees() != null) {
                        existingCourse.setAttenndees(course.getAttenndees());
                    }
                    if (course.getMaxsize() != null) {
                        existingCourse.setMaxsize(course.getMaxsize());
                    }

                    return existingCourse;
                }
            )
            .map(courseRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()));
    }

    /**
     * {@code GET  /courses} : get all the courses.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Courses");
        Page<Course> page;
        if (eagerload) {
            page = courseRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = courseRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /courses/:id} : get the "id" course.
     *
     * @param id the id of the course to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the course, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        Optional<Course> course = courseRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(course);
    }

    @GetMapping({ "/courses/types", "/courses/categories" })
    public ResponseEntity<List<String>> getExistingCourseTypes() {
        var res = courseRepository.getAllTypes().orElseThrow();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // name
    // class type
    // mindate
    // maxdate
    // mintime 09:00:00
    // maxtime 15:00:00
    // minduration
    // maxduration
    // maxintensity
    // minintensity
    // location

    @GetMapping("/courses/search")
    public ResponseEntity<List<Course>> searchCourse(
        @RequestParam(value = "mnt", required = false) String mnt,
        @RequestParam(value = "mxt", required = false) String mxt,
        @RequestParam(value = "mnd", required = false) String mnd,
        @RequestParam(value = "mxd", required = false) String mxd,
        @RequestParam(value = "mndr", required = false) String mndr,
        @RequestParam(value = "mxdr", required = false) String mxdr,
        @RequestParam(value = "mni", required = false) Long mni,
        @RequestParam(value = "mxi", required = false) Long mxi,
        @RequestParam(value = "type", required = false) String type,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "loc", required = false) String loc,
        @RequestParam(value = "mns", required = false) Long mns,
        @RequestParam(value = "mxs", required = false) Long mxs,
        @RequestParam(value = "nf", required = false) Long nf,
        @RequestParam(value = "ins", required = false) String ins
    ) {
        var all = courseRepository.findAll();

        //        System.out.println(mnt);
        //        System.out.println(mxt);
        //        System.out.println(mnd);
        //        System.out.println(mxd);
        //        all.stream().forEach(i -> System.out.println(i.getStarttime().toLocalTime()));

        //
        //        var t = mni;
        //                System.out.println(all.get(1).getIntensity());
        //        System.out.println(mni);
        //        System.out.println(mni>4);

        var res = all
            .stream()
            .filter(
                i ->
                    (
                        mnd == null || i.getStarttime() == null || i.getStarttime().toLocalDate().isAfter(LocalDate.parse(mnd).minusDays(1))
                    ) &&
                    (
                        mxd == null || i.getStarttime() == null || i.getStarttime().toLocalDate().isBefore(LocalDate.parse(mxd).plusDays(1))
                    ) &&
                    (
                        mnt == null ||
                        i.getStarttime() == null ||
                        i.getStarttime().toLocalTime().isAfter(LocalTime.parse(mnt).minusSeconds(1))
                    ) &&
                    (
                        mxt == null ||
                        i.getStarttime() == null ||
                        i.getStarttime().toLocalTime().isBefore(LocalTime.parse(mxt).plusSeconds(1))
                    ) &&
                    (mndr == null || i.getDuration() == null || i.getDuration().getSeconds() >= Duration.parse(mndr).getSeconds()) &&
                    (mxdr == null || i.getDuration() == null || i.getDuration().getSeconds() <= Duration.parse(mxdr).getSeconds()) &&
                    (mni == null || i.getIntensity() == null || i.getIntensity() >= mni) &&
                    (mxi == null || i.getIntensity() == null || i.getIntensity() <= mxi) &&
                    (
                        type == null ||
                        i.getType() == null ||
                        i.getType().toLowerCase(Locale.ROOT).contains(type.trim().toLowerCase(Locale.ROOT))
                    ) &&
                    (
                        name == null ||
                        i.getName() == null ||
                        i.getName().toLowerCase(Locale.ROOT).contains(name.trim().toLowerCase(Locale.ROOT))
                    ) &&
                    (
                        loc == null ||
                        i.getLocation() == null ||
                        i.getLocation().toLowerCase(Locale.ROOT).contains(loc.trim().toLowerCase(Locale.ROOT))
                    ) &&
                    (mns == null || i.getMaxsize() == null || i.getMaxsize() >= mns) &&
                    (mxs == null || i.getMaxsize() == null || i.getMaxsize() <= mxs) &&
                    (
                        nf == null ||
                        i.getAttenndees() == null ||
                        i.getMaxsize() == null ||
                        (i.getAttenndees() < i.getMaxsize()) &&
                        (nf == 1)
                    ) &&
                    (ins == null || i.getInstructor() == null || i.getInstructor().getLogin().equalsIgnoreCase(ins.trim()))
            )
            .collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * {@code DELETE  /courses/:id} : delete the "id" course.
     *
     * @param id the id of the course to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        var currentuser = userService.getUserWithAuthorities().orElseThrow();
        if (!userextraRepository.findOneById(currentuser.getId()).orElseThrow().getAccountype().equals("instructor")) {
            throw new BadRequestAlertException("Only instructor can edit a course", ENTITY_NAME, "notinstructor");
        }
        if (!courseRepository.findById(id).orElseThrow().getInstructor().getId().equals(currentuser.getId())) {
            throw new BadRequestAlertException("Only owner can delete this course", ENTITY_NAME, "notowner");
        }

        courseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
