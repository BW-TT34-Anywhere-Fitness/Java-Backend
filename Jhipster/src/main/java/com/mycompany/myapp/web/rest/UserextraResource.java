package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Userextra;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.repository.UserextraRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Userextra}.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@Transactional
public class UserextraResource {

    private final Logger log = LoggerFactory.getLogger(UserextraResource.class);

    private static final String ENTITY_NAME = "userextra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserextraRepository userextraRepository;

    private final UserRepository userRepository;

    public UserextraResource(UserextraRepository userextraRepository, UserRepository userRepository) {
        this.userextraRepository = userextraRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /userextras} : Create a new userextra.
     *
     * @param userextra the userextra to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userextra, or with status {@code 400 (Bad Request)} if the userextra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/userextras")
    public ResponseEntity<Userextra> createUserextra(@RequestBody Userextra userextra) throws URISyntaxException {
        log.debug("REST request to save Userextra : {}", userextra);
        if (userextra.getId() != null) {
            throw new BadRequestAlertException("A new userextra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(userextra.getUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Long userId = userextra.getUser().getId();
        userRepository.findById(userId).ifPresent(userextra::user);
        Userextra result = userextraRepository.save(userextra);
        return ResponseEntity
            .created(new URI("/api/userextras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /userextras/:id} : Updates an existing userextra.
     *
     * @param id the id of the userextra to save.
     * @param userextra the userextra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userextra,
     * or with status {@code 400 (Bad Request)} if the userextra is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userextra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/userextras/{id}")
    public ResponseEntity<Userextra> updateUserextra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Userextra userextra
    ) throws URISyntaxException {
        log.debug("REST request to update Userextra : {}, {}", id, userextra);
        if (userextra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userextra.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userextraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Userextra result = userextraRepository.save(userextra);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userextra.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /userextras/:id} : Partial updates given fields of an existing userextra, field will ignore if it is null
     *
     * @param id the id of the userextra to save.
     * @param userextra the userextra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userextra,
     * or with status {@code 400 (Bad Request)} if the userextra is not valid,
     * or with status {@code 404 (Not Found)} if the userextra is not found,
     * or with status {@code 500 (Internal Server Error)} if the userextra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/userextras/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Userextra> partialUpdateUserextra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Userextra userextra
    ) throws URISyntaxException {
        log.debug("REST request to partial update Userextra partially : {}, {}", id, userextra);
        if (userextra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userextra.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userextraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Userextra> result = userextraRepository
            .findById(userextra.getId())
            .map(
                existingUserextra -> {
                    if (userextra.getAccountype() != null) {
                        existingUserextra.setAccountype(userextra.getAccountype());
                    }

                    return existingUserextra;
                }
            )
            .map(userextraRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userextra.getId().toString())
        );
    }

    /**
     * {@code GET  /userextras} : get all the userextras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userextras in body.
     */
    @GetMapping("/userextras")
    @Transactional(readOnly = true)
    public List<Userextra> getAllUserextras() {
        log.debug("REST request to get all Userextras");
        return userextraRepository.findAll();
    }

    /**
     * {@code GET  /userextras/:id} : get the "id" userextra.
     *
     * @param id the id of the userextra to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userextra, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/userextras/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Userextra> getUserextra(@PathVariable Long id) {
        log.debug("REST request to get Userextra : {}", id);
        Optional<Userextra> userextra = userextraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userextra);
    }

    /**
     * {@code DELETE  /userextras/:id} : delete the "id" userextra.
     *
     * @param id the id of the userextra to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/userextras/{id}")
    public ResponseEntity<Void> deleteUserextra(@PathVariable Long id) {
        log.debug("REST request to delete Userextra : {}", id);
        userextraRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
