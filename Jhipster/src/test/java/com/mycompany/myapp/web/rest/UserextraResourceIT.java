package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Userextra;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.repository.UserextraRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserextraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserextraResourceIT {

    private static final String DEFAULT_ACCOUNTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/userextras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserextraRepository userextraRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserextraMockMvc;

    private Userextra userextra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Userextra createEntity(EntityManager em) {
        Userextra userextra = new Userextra().accountype(DEFAULT_ACCOUNTYPE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userextra.setUser(user);
        return userextra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Userextra createUpdatedEntity(EntityManager em) {
        Userextra userextra = new Userextra().accountype(UPDATED_ACCOUNTYPE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userextra.setUser(user);
        return userextra;
    }

    @BeforeEach
    public void initTest() {
        userextra = createEntity(em);
    }

    @Test
    @Transactional
    void createUserextra() throws Exception {
        int databaseSizeBeforeCreate = userextraRepository.findAll().size();
        // Create the Userextra
        restUserextraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userextra)))
            .andExpect(status().isCreated());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeCreate + 1);
        Userextra testUserextra = userextraList.get(userextraList.size() - 1);
        assertThat(testUserextra.getAccountype()).isEqualTo(DEFAULT_ACCOUNTYPE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testUserextra.getId()).isEqualTo(testUserextra.getUser().getId());
    }

    @Test
    @Transactional
    void createUserextraWithExistingId() throws Exception {
        // Create the Userextra with an existing ID
        userextra.setId(1L);

        int databaseSizeBeforeCreate = userextraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserextraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userextra)))
            .andExpect(status().isBadRequest());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateUserextraMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);
        int databaseSizeBeforeCreate = userextraRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the userextra
        Userextra updatedUserextra = userextraRepository.findById(userextra.getId()).get();
        assertThat(updatedUserextra).isNotNull();
        // Disconnect from session so that the updates on updatedUserextra are not directly saved in db
        em.detach(updatedUserextra);

        // Update the User with new association value
        updatedUserextra.setUser(user);

        // Update the entity
        restUserextraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserextra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserextra))
            )
            .andExpect(status().isOk());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeCreate);
        Userextra testUserextra = userextraList.get(userextraList.size() - 1);
        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testUserextra.getId()).isEqualTo(testUserextra.getUser().getId());
    }

    @Test
    @Transactional
    void getAllUserextras() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);

        // Get all the userextraList
        restUserextraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userextra.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountype").value(hasItem(DEFAULT_ACCOUNTYPE)));
    }

    @Test
    @Transactional
    void getUserextra() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);

        // Get the userextra
        restUserextraMockMvc
            .perform(get(ENTITY_API_URL_ID, userextra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userextra.getId().intValue()))
            .andExpect(jsonPath("$.accountype").value(DEFAULT_ACCOUNTYPE));
    }

    @Test
    @Transactional
    void getNonExistingUserextra() throws Exception {
        // Get the userextra
        restUserextraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserextra() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);

        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();

        // Update the userextra
        Userextra updatedUserextra = userextraRepository.findById(userextra.getId()).get();
        // Disconnect from session so that the updates on updatedUserextra are not directly saved in db
        em.detach(updatedUserextra);
        updatedUserextra.accountype(UPDATED_ACCOUNTYPE);

        restUserextraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserextra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserextra))
            )
            .andExpect(status().isOk());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
        Userextra testUserextra = userextraList.get(userextraList.size() - 1);
        assertThat(testUserextra.getAccountype()).isEqualTo(UPDATED_ACCOUNTYPE);
    }

    @Test
    @Transactional
    void putNonExistingUserextra() throws Exception {
        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();
        userextra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserextraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userextra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userextra))
            )
            .andExpect(status().isBadRequest());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserextra() throws Exception {
        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();
        userextra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserextraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userextra))
            )
            .andExpect(status().isBadRequest());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserextra() throws Exception {
        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();
        userextra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserextraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userextra)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserextraWithPatch() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);

        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();

        // Update the userextra using partial update
        Userextra partialUpdatedUserextra = new Userextra();
        partialUpdatedUserextra.setId(userextra.getId());

        restUserextraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserextra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserextra))
            )
            .andExpect(status().isOk());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
        Userextra testUserextra = userextraList.get(userextraList.size() - 1);
        assertThat(testUserextra.getAccountype()).isEqualTo(DEFAULT_ACCOUNTYPE);
    }

    @Test
    @Transactional
    void fullUpdateUserextraWithPatch() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);

        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();

        // Update the userextra using partial update
        Userextra partialUpdatedUserextra = new Userextra();
        partialUpdatedUserextra.setId(userextra.getId());

        partialUpdatedUserextra.accountype(UPDATED_ACCOUNTYPE);

        restUserextraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserextra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserextra))
            )
            .andExpect(status().isOk());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
        Userextra testUserextra = userextraList.get(userextraList.size() - 1);
        assertThat(testUserextra.getAccountype()).isEqualTo(UPDATED_ACCOUNTYPE);
    }

    @Test
    @Transactional
    void patchNonExistingUserextra() throws Exception {
        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();
        userextra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserextraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userextra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userextra))
            )
            .andExpect(status().isBadRequest());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserextra() throws Exception {
        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();
        userextra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserextraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userextra))
            )
            .andExpect(status().isBadRequest());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserextra() throws Exception {
        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();
        userextra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserextraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userextra))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserextra() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);

        int databaseSizeBeforeDelete = userextraRepository.findAll().size();

        // Delete the userextra
        restUserextraMockMvc
            .perform(delete(ENTITY_API_URL_ID, userextra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
