package com.quikr.jobsopstools.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quikr.jobsopstools.JobsOpsToolsApp;
import com.quikr.jobsopstools.domain.JobsUser;
import com.quikr.jobsopstools.domain.enumeration.Permission;
import com.quikr.jobsopstools.repository.JobsUserRepository;
import com.quikr.jobsopstools.service.JobsUserService;
import com.quikr.jobsopstools.service.dto.JobsUserDTO;
import com.quikr.jobsopstools.service.mapper.JobsUserMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JobsUserResource} REST controller.
 */
@SpringBootTest(classes = JobsOpsToolsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobsUserResourceIT {
    private static final Long DEFAULT_BABEL_USER_ID = 1L;
    private static final Long UPDATED_BABEL_USER_ID = 2L;

    private static final String DEFAULT_EMAIL = "-@hjp/";
    private static final String UPDATED_EMAIL = "P;<>@l1'5J";

    private static final Long DEFAULT_PHONE = 1000000000L;
    private static final Long UPDATED_PHONE = 1000000001L;

    private static final Permission DEFAULT_PERMISSION = Permission.ADMIN_ACCESS;
    private static final Permission UPDATED_PERMISSION = Permission.VIEW_ONLY_ACCESS;

    @Autowired
    private JobsUserRepository jobsUserRepository;

    @Autowired
    private JobsUserMapper jobsUserMapper;

    @Autowired
    private JobsUserService jobsUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobsUserMockMvc;

    private JobsUser jobsUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobsUser createEntity(EntityManager em) {
        JobsUser jobsUser = new JobsUser()
            .babelUserId(DEFAULT_BABEL_USER_ID)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .permission(DEFAULT_PERMISSION);
        return jobsUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobsUser createUpdatedEntity(EntityManager em) {
        JobsUser jobsUser = new JobsUser()
            .babelUserId(UPDATED_BABEL_USER_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .permission(UPDATED_PERMISSION);
        return jobsUser;
    }

    @BeforeEach
    public void initTest() {
        jobsUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobsUser() throws Exception {
        int databaseSizeBeforeCreate = jobsUserRepository.findAll().size();
        // Create the JobsUser
        JobsUserDTO jobsUserDTO = jobsUserMapper.toDto(jobsUser);
        restJobsUserMockMvc
            .perform(
                post("/api/jobs-users").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobsUserDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JobsUser in the database
        List<JobsUser> jobsUserList = jobsUserRepository.findAll();
        assertThat(jobsUserList).hasSize(databaseSizeBeforeCreate + 1);
        JobsUser testJobsUser = jobsUserList.get(jobsUserList.size() - 1);
        assertThat(testJobsUser.getBabelUserId()).isEqualTo(DEFAULT_BABEL_USER_ID);
        assertThat(testJobsUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testJobsUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testJobsUser.getPermission()).isEqualTo(DEFAULT_PERMISSION);
    }

    @Test
    @Transactional
    public void createJobsUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobsUserRepository.findAll().size();

        // Create the JobsUser with an existing ID
        jobsUser.setId(1L);
        JobsUserDTO jobsUserDTO = jobsUserMapper.toDto(jobsUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobsUserMockMvc
            .perform(
                post("/api/jobs-users").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobsUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobsUser in the database
        List<JobsUser> jobsUserList = jobsUserRepository.findAll();
        assertThat(jobsUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobsUsers() throws Exception {
        // Initialize the database
        jobsUserRepository.saveAndFlush(jobsUser);

        // Get all the jobsUserList
        restJobsUserMockMvc
            .perform(get("/api/jobs-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobsUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].babelUserId").value(hasItem(DEFAULT_BABEL_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())));
    }

    @Test
    @Transactional
    public void getJobsUser() throws Exception {
        // Initialize the database
        jobsUserRepository.saveAndFlush(jobsUser);

        // Get the jobsUser
        restJobsUserMockMvc
            .perform(get("/api/jobs-users/{id}", jobsUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobsUser.getId().intValue()))
            .andExpect(jsonPath("$.babelUserId").value(DEFAULT_BABEL_USER_ID.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobsUser() throws Exception {
        // Get the jobsUser
        restJobsUserMockMvc.perform(get("/api/jobs-users/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobsUser() throws Exception {
        // Initialize the database
        jobsUserRepository.saveAndFlush(jobsUser);

        int databaseSizeBeforeUpdate = jobsUserRepository.findAll().size();

        // Update the jobsUser
        JobsUser updatedJobsUser = jobsUserRepository.findById(jobsUser.getId()).get();
        // Disconnect from session so that the updates on updatedJobsUser are not directly saved in db
        em.detach(updatedJobsUser);
        updatedJobsUser.babelUserId(UPDATED_BABEL_USER_ID).email(UPDATED_EMAIL).phone(UPDATED_PHONE).permission(UPDATED_PERMISSION);
        JobsUserDTO jobsUserDTO = jobsUserMapper.toDto(updatedJobsUser);

        restJobsUserMockMvc
            .perform(put("/api/jobs-users").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobsUserDTO)))
            .andExpect(status().isOk());

        // Validate the JobsUser in the database
        List<JobsUser> jobsUserList = jobsUserRepository.findAll();
        assertThat(jobsUserList).hasSize(databaseSizeBeforeUpdate);
        JobsUser testJobsUser = jobsUserList.get(jobsUserList.size() - 1);
        assertThat(testJobsUser.getBabelUserId()).isEqualTo(UPDATED_BABEL_USER_ID);
        assertThat(testJobsUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testJobsUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testJobsUser.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingJobsUser() throws Exception {
        int databaseSizeBeforeUpdate = jobsUserRepository.findAll().size();

        // Create the JobsUser
        JobsUserDTO jobsUserDTO = jobsUserMapper.toDto(jobsUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobsUserMockMvc
            .perform(put("/api/jobs-users").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobsUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobsUser in the database
        List<JobsUser> jobsUserList = jobsUserRepository.findAll();
        assertThat(jobsUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobsUser() throws Exception {
        // Initialize the database
        jobsUserRepository.saveAndFlush(jobsUser);

        int databaseSizeBeforeDelete = jobsUserRepository.findAll().size();

        // Delete the jobsUser
        restJobsUserMockMvc
            .perform(delete("/api/jobs-users/{id}", jobsUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobsUser> jobsUserList = jobsUserRepository.findAll();
        assertThat(jobsUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
